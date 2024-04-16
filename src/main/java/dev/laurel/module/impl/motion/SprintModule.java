package dev.laurel.module.impl.motion;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import lombok.Getter;

import static dev.laurel.client.IMinecraft.mc;

@Getter
@ModuleInfo(name = "Sprint", description = "Makes you sprint", moduleCategory = ModuleCategory.MOTION)
public final class SprintModule extends Module {

    private final BooleanSetting testBoolean = new BooleanSetting("TestBoolean", false);
    private final ModeSetting testMode = new ModeSetting("TestMode", "Easy", "Normal", "Hard");
    private final NumberSetting testNumber = new NumberSetting("TestNumber", 1, 0, 10, 1);

    public SprintModule() {
        this.setEnabled(true);
        this.addSettings(this.testBoolean, this.testMode, this.testNumber);
    }

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        mc.gameSettings.keyBindSprint.setPressed(true);
    };
}
