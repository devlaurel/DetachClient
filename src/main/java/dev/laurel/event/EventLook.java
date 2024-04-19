package dev.laurel.event;

import dev.codeman.eventbus.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class EventLook extends Event {
    private float yaw, pitch;
}
