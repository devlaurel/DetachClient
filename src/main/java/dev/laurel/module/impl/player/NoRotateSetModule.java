package dev.laurel.module.impl.player;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.event.EventPacketReceive;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "NoRotateSet", description = "Prevents you from getting rotated", moduleCategory = ModuleCategory.PLAYER)
public final class NoRotateSetModule extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Packet", "Edit");

    public NoRotateSetModule() {
        this.addSettings(this.mode);
    }

    @EventHandler
    private final Listener<EventPacketReceive> eventPacketReceiveListener = event -> {
        final Packet<?> packet = event.getPacket();
        if (packet instanceof S08PacketPlayerPosLook) {
            switch (this.mode.getCurrentMode()) {
                case "Packet":
                    event.setCancelled(true);
                    break;
                case "Edit":
                    ((S08PacketPlayerPosLook) packet).setYaw(mc.thePlayer.rotationYaw);
                    ((S08PacketPlayerPosLook) packet).setPitch(mc.thePlayer.rotationPitch);
                    break;
            }
        }
    };
}
