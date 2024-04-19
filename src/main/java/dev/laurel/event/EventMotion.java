package dev.laurel.event;

import dev.codeman.eventbus.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class EventMotion extends Event {
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;
}
