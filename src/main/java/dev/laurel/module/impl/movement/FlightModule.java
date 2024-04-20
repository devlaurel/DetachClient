package dev.laurel.module.impl.movement;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "Flight", description = "Allows you to fly", moduleCategory = ModuleCategory.MOVEMENT)
public final class FlightModule extends Module {

    private final NumberSetting flightSpeed = new NumberSetting("FlightSpeed", 1, 0.1, 10, 0.1);

    public FlightModule() {
        this.addSettings(this.flightSpeed);
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> this.setSuffix(String.valueOf(this.flightSpeed.getValue()));

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        mc.thePlayer.capabilities.isFlying = true;
        mc.thePlayer.capabilities.setFlySpeed((float) (this.flightSpeed.getValue() / 20F));
    };

    @Override
    protected void onDisable() {
        super.onDisable();
        mc.thePlayer.capabilities.isFlying = false;
        mc.thePlayer.capabilities.setFlySpeed(1F);
    }
}
