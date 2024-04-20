package dev.laurel.module.impl.movement;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "Step", description = "Makes you step up blocks", moduleCategory = ModuleCategory.MOVEMENT)
public final class StepModule extends Module {

    private final NumberSetting stepHeight = new NumberSetting("StepHeight", 1, 1, 4, 0.5);

    public StepModule() {
        this.addSettings(this.stepHeight);
    }

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        mc.thePlayer.stepHeight = (float) stepHeight.getValue();
    };

    @Override
    protected void onDisable() {
        super.onDisable();
        mc.thePlayer.stepHeight = 0.6F;
    }
}
