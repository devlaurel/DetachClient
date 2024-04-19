package dev.laurel.event;

import dev.codeman.eventbus.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class EventMoveFlying extends Event {
    private float yaw;
}

