package dev.laurel.module.impl.combat;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.event.EventPacketReceive;
import dev.laurel.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

import static dev.laurel.client.IMinecraft.mc;

public final class NoVelocityModule extends Module {

    @EventHandler
    private final Listener<EventPacketReceive> eventPacketReceiveListener = event -> {
        Packet<?> packet = event.getPacket();
        if (packet instanceof S12PacketEntityVelocity && mc.thePlayer.getEntityId() == ((S12PacketEntityVelocity) packet).getEntityID()) {
            event.setCancelled(true);
        }
    };
}
