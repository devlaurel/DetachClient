package dev.laurel.module.impl.player;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.event.EventMotion;
import dev.laurel.event.EventRender2D;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import net.minecraft.network.play.client.C03PacketPlayer;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "NoFall", description = "Prevents you from getting fall damage", moduleCategory = ModuleCategory.PLAYER)
public final class NoFallModule extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Packet", "GroundSpoof");

    public NoFallModule() {
        this.addSettings(this.mode);
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> this.setSuffix(String.valueOf(this.mode.getCurrentMode()));

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        switch (this.mode.getCurrentMode()) {
            case "Packet":
                if (mc.thePlayer.fallDistance > 2) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                }
                break;
        }
    };

    @EventHandler
    private final Listener<EventMotion> eventMotionListener = event -> {
        switch (this.mode.getCurrentMode()) {
            case "GroundSpoof":
                if (mc.thePlayer.fallDistance > 2) {
                    event.setOnGround(true);
                }
                break;
        }
    };
}
