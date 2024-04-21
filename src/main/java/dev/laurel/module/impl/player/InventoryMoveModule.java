package dev.laurel.module.impl.player;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.clickgui.panel.PanelClickGUI;
import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;

import java.util.Objects;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "InventoryMove", description = "Allows you to move in GUIs", moduleCategory = ModuleCategory.PLAYER)
public final class InventoryMoveModule extends Module {

    private final ModeSetting modeSetting = new ModeSetting("Mode", "ClickGUI", "Vanilla");

    public InventoryMoveModule() {
        this.addSettings(this.modeSetting);
        this.setEnabled(true);
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> this.setSuffix(this.modeSetting.getCurrentMode());

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        if (mc.currentScreen instanceof GuiChat) return;

        if (Objects.equals(this.modeSetting.getCurrentMode(), "ClickGUI") && !(mc.currentScreen instanceof PanelClickGUI)) return;

        mc.gameSettings.keyBindForward.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
        mc.gameSettings.keyBindBack.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
        mc.gameSettings.keyBindLeft.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
        mc.gameSettings.keyBindRight.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
        mc.gameSettings.keyBindJump.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));
        mc.gameSettings.keyBindSprint.setPressed(Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode()));
    };
}
