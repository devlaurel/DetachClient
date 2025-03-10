package dev.laurel.module.impl.combat;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import dev.laurel.util.time.TimeHelper;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "AutoClicker", description = "Automatically clicks your mouse", moduleCategory = ModuleCategory.COMBAT)
public final class AutoClickerModule extends Module {

    private final TimeHelper timeHelper = new TimeHelper();

    private final NumberSetting cps = new NumberSetting("CPS", 8, 1, 20, 0.5);

    public AutoClickerModule() {
        this.addSettings(this.cps);
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> this.setSuffix(String.valueOf(this.cps.getValue()));

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        if (!mc.gameSettings.keyBindAttack.isKeyDown()) return;

        mc.leftClickCounter = 0;
        if (this.timeHelper.hasPassed((long) (1000 / this.cps.getValue()))) {
            mc.clickMouse();
            this.timeHelper.reset();
        }
    };
}
