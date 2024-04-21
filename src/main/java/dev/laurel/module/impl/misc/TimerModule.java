package dev.laurel.module.impl.misc;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "Timer", description = "Modifies the game speed", moduleCategory = ModuleCategory.MISC)
public final class TimerModule extends Module {

    private final NumberSetting timerSpeed = new NumberSetting("TimerSpeed", 1, 0.1, 10, 1);

    public TimerModule() {
        this.addSettings(this.timerSpeed);
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> this.setSuffix(String.valueOf(this.timerSpeed.getValue()));

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        mc.getTimer().timerSpeed = ((float) this.timerSpeed.getValue());
    };

    @Override
    protected void onDisable() {
        super.onDisable();
        mc.getTimer().timerSpeed = 1.0F;
    }
}
