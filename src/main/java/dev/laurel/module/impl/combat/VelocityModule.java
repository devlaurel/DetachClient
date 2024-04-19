package dev.laurel.module.impl.combat;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.event.EventPacketReceive;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "Velocity", description = "Removes/Reduces your taken knockback", moduleCategory = ModuleCategory.COMBAT)
public final class VelocityModule extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Packet");

    public VelocityModule() {
        this.addSettings(this.mode);
    }

    @EventHandler
    private final Listener<EventPacketReceive> eventPacketReceiveListener = event -> {
        final Packet<?> packet = event.getPacket();
        switch (this.mode.getCurrentMode()) {
            case "Packet":
                if (packet instanceof S12PacketEntityVelocity && mc.thePlayer.getEntityId() == ((S12PacketEntityVelocity) packet).getEntityID()) {
                    event.setCancelled(true);
                }
                break;
        }
    };
}
