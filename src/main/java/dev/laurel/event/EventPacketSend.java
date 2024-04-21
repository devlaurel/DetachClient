package dev.laurel.event;

import dev.codeman.eventbus.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.network.Packet;

@Getter
@AllArgsConstructor
public final class EventPacketSend extends Event {
    private final Packet<?> packet;
}
