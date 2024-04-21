package dev.laurel.module.impl.fun;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventUpdate;
import dev.laurel.handler.RotationHandler;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;

@ModuleInfo(name = "SpinBot", description = "Useless but fun module", moduleCategory = ModuleCategory.FUN)
public final class SpinBotModule extends Module {

    private float spin = 0;

    private final NumberSetting spinSpeed = new NumberSetting("SpinSpeed", 15, 1, 45, 1);
    private final ModeSetting spinDirection = new ModeSetting("SpinDirection", "Positive", "Negative");

    public SpinBotModule() {
        this.addSettings(this.spinSpeed, this.spinDirection);
    }

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        if (this.spinDirection.getCurrentMode().equals("Positive")) {
            this.spin += (float) this.spinSpeed.getValue();
        } else {
            this.spin -= (float) this.spinSpeed.getValue();
        }

        RotationHandler.INSTANCE.setServersideYaw(RotationHandler.INSTANCE.getServersideYaw() + this.spin);
    };

    @Override
    protected void onDisable() {
        this.spin = 0;
        super.onDisable();
    }
}