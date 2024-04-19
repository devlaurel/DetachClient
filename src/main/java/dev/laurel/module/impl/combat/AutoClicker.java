package dev.laurel.module.impl.combat;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import dev.laurel.util.TimeHelper;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "AutoClicker", description = "Automatically clicks your mouse", moduleCategory = ModuleCategory.COMBAT)
public final class AutoClicker extends Module {

    private final TimeHelper timeHelper = new TimeHelper();

    private final NumberSetting cps = new NumberSetting("CPS", 8, 1, 20, 0.5);

    public AutoClicker() {
        this.addSettings(this.cps);
    }

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        if (!mc.gameSettings.keyBindAttack.isKeyDown()) return;

        mc.setLeftClickCounter(0);
        if (timeHelper.hasPassed((long) (1000 / this.cps.getValue()))) {
            mc.clickMouse();
            timeHelper.reset();
        }
    };
}
