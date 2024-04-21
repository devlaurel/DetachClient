package dev.laurel.module.impl.combat;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "NoClickDelay", description = "Removes the click delay", moduleCategory = ModuleCategory.COMBAT)
public final class NoClickDelayModule extends Module {

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event ->  mc.leftClickCounter = 0;
}
