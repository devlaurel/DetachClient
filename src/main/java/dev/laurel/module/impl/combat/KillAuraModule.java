package dev.laurel.module.impl.combat;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.event.EventUpdate;
import dev.laurel.handler.RotationHandler;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import dev.laurel.util.math.RandomUtil;
import dev.laurel.util.player.RotationUtil;
import dev.laurel.util.time.TimeHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "KillAura", description = "Attacks nearby entities", moduleCategory = ModuleCategory.COMBAT)
public final class KillAuraModule extends Module {

    private List<EntityLivingBase> targets;

    private final TimeHelper timeHelper = new TimeHelper();

    private final NumberSetting cps = new NumberSetting("CPS", 8, 1, 20, 0.1);
    private final NumberSetting range = new NumberSetting("Range", 3, 1, 6, 0.1);
    private final ModeSetting clickMode = new ModeSetting("ClickMode", "RealClick", "Packet");
    private final BooleanSetting rotate = new BooleanSetting("Rotate", true);
    private final NumberSetting rotationRandomization = new NumberSetting("RotationRandomization", 2, 0, 6, 0.1);
    private final BooleanSetting targetPlayers = new BooleanSetting("TargetPlayers", true);
    private final BooleanSetting targetMonsters = new BooleanSetting("TargetMonsters", false);
    private final BooleanSetting targetAnimals = new BooleanSetting("TargetAnimals", false);

    public KillAuraModule() {
        this.addSettings(this.cps, this.clickMode, this.rotate, this.rotationRandomization, this.targetPlayers, this.targetMonsters, this.targetAnimals);
        targets = new ArrayList<>();
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> this.setSuffix(String.valueOf(this.targets.size()));

    @EventHandler(priority = 1)
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        List<EntityLivingBase> livingEntities =
                mc.theWorld.getLoadedEntityList().stream()
                        .filter(entity -> entity instanceof EntityLivingBase)
                        .map(entity -> (EntityLivingBase) entity)
                        .collect(Collectors.toList());

        this.targets = livingEntities.stream()
                .filter(entity -> entity != mc.thePlayer && entity.getDistanceToEntity(mc.thePlayer) < this.range.getValue())
                .filter(entity -> (this.targetPlayers.isEnabled() && entity instanceof EntityPlayer) ||
                        (this.targetMonsters.isEnabled() && entity instanceof EntityMob) ||
                        (this.targetAnimals.isEnabled() && entity instanceof EntityAnimal))
                .sorted(Comparator.comparingDouble(entity -> mc.thePlayer.getDistanceToEntity(entity)))
                .collect(Collectors.toList());
        if (this.targets.isEmpty()) return;
        Entity target = this.targets.get(0);

        float[] rotationsToTarget = RotationUtil.getClosestHitVec3Rotation(mc.thePlayer.getPositionEyes(1F), target.getEntityBoundingBox());

        float yawToTarget = rotationsToTarget[0];
        float pitchToTarget = rotationsToTarget[1];

        if (this.rotate.isEnabled()) {
            RotationHandler.INSTANCE.setServersideYaw((float) (yawToTarget + RandomUtil.randomDouble(-this.rotationRandomization.getValue(), (float) this.rotationRandomization.getValue())));
            RotationHandler.INSTANCE.setServersidePitch((float) (pitchToTarget + RandomUtil.randomDouble(-this.rotationRandomization.getValue(), (float) this.rotationRandomization.getValue())));
        }

        if (this.timeHelper.hasPassed((long) (1000 / this.cps.getValue() * RandomUtil.randomDouble(0.1, 2)))) {
            if (mc.thePlayer.isUsingItem()) return;
            switch (this.clickMode.getCurrentMode()) {
                case "RealClick":
                    mc.clickMouse();
                    break;
                case "Packet":
                    mc.thePlayer.swingItem();
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
                    break;
            }
            this.timeHelper.reset();
        }
    };
}
