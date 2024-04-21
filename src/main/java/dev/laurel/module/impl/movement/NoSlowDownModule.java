package dev.laurel.module.impl.movement;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.event.EventSlowdown;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;

@ModuleInfo(name = "NoSlowDown", description = "Prevents you from slowing down",moduleCategory = ModuleCategory.MOVEMENT)
public final class NoSlowDownModule extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla");

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> this.setSuffix(String.valueOf(this.mode.getCurrentMode()));

    public NoSlowDownModule() {
        this.addSettings(this.mode);
    }

    @EventHandler
    private final Listener<EventSlowdown> eventSlowdownListener = event -> {
        switch (this.mode.getCurrentMode()) {
            case "Vanilla":
                event.setCancelled(true);
                break;
        }
    };
}
