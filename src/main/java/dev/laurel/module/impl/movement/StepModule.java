package dev.laurel.module.impl.movement;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "Step", description = "Makes you step up blocks", moduleCategory = ModuleCategory.MOVEMENT)
public final class StepModule extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Vanilla");

    public StepModule() {
        this.addSettings(this.mode);
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> this.setSuffix(String.valueOf(this.mode.getCurrentMode()));

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        switch (this.mode.getCurrentMode()) {
            case "Vanilla":
                mc.thePlayer.stepHeight = 1F;
                break;
        }
    };

    @Override
    protected void onDisable() {
        super.onDisable();
        mc.thePlayer.stepHeight = 0.6F;
    }
}
