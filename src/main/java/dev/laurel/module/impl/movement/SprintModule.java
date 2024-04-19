package dev.laurel.module.impl.movement;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import lombok.Getter;

import static dev.laurel.client.IMinecraft.mc;

@Getter
@ModuleInfo(name = "Sprint", description = "Makes you sprint", moduleCategory = ModuleCategory.MOVEMENT)
public final class SprintModule extends Module {

    public SprintModule() {
        this.setEnabled(true);
    }

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        mc.gameSettings.keyBindSprint.setPressed(true);
    };
}
