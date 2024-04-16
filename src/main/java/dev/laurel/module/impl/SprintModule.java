package dev.laurel.module.impl;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "Sprint", description = "Makes you sprint", moduleCategory = ModuleCategory.MOTION)
public final class SprintModule extends Module {

    public SprintModule() {
        this.setEnabled(true);
    }

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        mc.gameSettings.keyBindSprint.setPressed(true);
    };
}
