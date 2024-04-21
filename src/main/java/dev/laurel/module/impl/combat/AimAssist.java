package dev.laurel.module.impl.combat;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventMotion;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import dev.laurel.util.player.RotationUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "AimAssist", description = "Improves your aim", moduleCategory = ModuleCategory.COMBAT)
public final class AimAssist extends Module {

    private List<EntityLivingBase> targets;

    private final NumberSetting range = new NumberSetting("Range", 4, 1, 6, 0.1);
    private final NumberSetting aimSpeed = new NumberSetting("AimSpeed", 1, 0.1, 1, 0.05);
    private final BooleanSetting targetPlayers = new BooleanSetting("TargetPlayers", true);
    private final BooleanSetting targetMonsters = new BooleanSetting("TargetMonsters", false);
    private final BooleanSetting targetAnimals = new BooleanSetting("TargetAnimals", false);

    public AimAssist() {
        this.addSettings(this.range, this.aimSpeed, this.targetPlayers, this.targetMonsters, this.targetAnimals);
        this.targets = new ArrayList<>();
    }

    @EventHandler
    private final Listener<EventMotion> eventUpdateListener = event -> {
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

        float deltaYaw = MathHelper.wrapAngleTo180_float(yawToTarget - mc.thePlayer.rotationYaw);
        float deltaPitch = MathHelper.wrapAngleTo180_float(pitchToTarget - mc.thePlayer.rotationPitch);
        mc.thePlayer.rotationYaw += (float) (deltaYaw * (this.aimSpeed.getValue() / 10F));
        mc.thePlayer.rotationPitch += (float) (deltaPitch * (this.aimSpeed.getValue() / 10F));
    };
}
