package dev.laurel.module.impl.movement;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "NoJumpDelay", description = "Removes your jump delay", moduleCategory = ModuleCategory.MOVEMENT)
public final class NoJumpDelayModule extends Module {

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        mc.thePlayer.setJumpTicks(0);
    };
}
