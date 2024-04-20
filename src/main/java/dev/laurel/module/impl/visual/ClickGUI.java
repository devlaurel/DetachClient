package dev.laurel.module.impl.visual;

import dev.laurel.client.clickgui.panel.PanelClickGUI;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import org.lwjgl.input.Keyboard;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "ClickGUI", description = "Displays a ClickGUI", moduleCategory = ModuleCategory.VISUAL)
public final class ClickGUI extends Module {

    private final ModeSetting clickGui = new ModeSetting("Mode", "Augustus");

    public ClickGUI() {
        this.setKey(Keyboard.KEY_RSHIFT);
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        switch (this.clickGui.getCurrentMode()) {
            case "Augustus":
            mc.displayGuiScreen(PanelClickGUI.INSTANCE);
            break;
        }

        this.setEnabled(false);
    }
}
