package dev.laurel.module.impl.misc;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "Timer", description = "Modifies the game speed", moduleCategory = ModuleCategory.MISC)
public final class TimerModule extends Module {

    private final NumberSetting speed = new NumberSetting("Speed", 1, 0.1, 10, 1);

    public TimerModule() {
        this.addSettings(this.speed);
    }

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        mc.getTimer().setTimerSpeed((float) this.speed.getValue());
    };

    @Override
    protected void onDisable() {
        super.onDisable();
        mc.getTimer().setTimerSpeed(1F);
    }
}
