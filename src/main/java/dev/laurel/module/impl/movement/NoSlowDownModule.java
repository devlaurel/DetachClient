package dev.laurel.module.impl.movement;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.event.EventSlowdown;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;

@ModuleInfo(name = "NoSlowDown", description = "Prevents you from slowing down",moduleCategory = ModuleCategory.MOVEMENT)
public final class NoSlowDownModule extends Module {

    public NoSlowDownModule() {

    }

    @EventHandler
    private final Listener<EventSlowdown> eventSlowdownListener = event -> {
        event.setCancelled(true);
    };
}
