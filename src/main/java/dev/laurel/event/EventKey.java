package dev.laurel.event;

import dev.codeman.eventbus.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class EventKey extends Event {
    private final int key;
}
