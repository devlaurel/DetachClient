package dev.laurel.module.impl.movement;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "AutoWalk", description = "Automatically walks for you", moduleCategory = ModuleCategory.MOVEMENT)
public class AutoWalkModule extends Module {

    private final ModeSetting direction = new ModeSetting("Direction", "None", "Forward", "Backward");
    private final ModeSetting secondDirection = new ModeSetting("Direction", "None", "Left", "Right");
    private final ModeSetting jumpMode = new ModeSetting("Jump", "None", "Always");

    public AutoWalkModule() {
        this.addSettings(this.direction, this.secondDirection, this.jumpMode);
    }

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        switch (this.direction.getCurrentMode()) {
            case "None":
                break;
                case "Forward":
                    mc.gameSettings.keyBindForward.setPressed(true);
                    break;
                    case "Backward":
                        mc.gameSettings.keyBindBack.setPressed(true);
                        break;
        }

        switch (this.secondDirection.getCurrentMode()) {
            case "None":
                break;
                case "Left":
                    mc.gameSettings.keyBindLeft.setPressed(true);
                    break;
                    case "Right":
                        mc.gameSettings.keyBindRight.setPressed(true);
                        break;
        }


        switch (this.jumpMode.getCurrentMode()) {
            case "None":
                break;
                case "Always":
                    mc.gameSettings.keyBindJump.setPressed(true);
                    break;
        }
    };

    @Override
    protected void onDisable() {
        super.onDisable();
        mc.gameSettings.keyBindForward.unpressKey();
        mc.gameSettings.keyBindBack.unpressKey();
        mc.gameSettings.keyBindLeft.unpressKey();
        mc.gameSettings.keyBindRight.unpressKey();
        mc.gameSettings.keyBindJump.unpressKey();
    }
}
