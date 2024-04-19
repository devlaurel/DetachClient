package dev.laurel.client.clickgui.panel.component.impl;

import dev.laurel.client.clickgui.panel.PanelClickGUI;
import dev.laurel.client.clickgui.panel.component.Component;
import dev.laurel.client.setting.Setting;
import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.module.Module;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

import static dev.laurel.client.IMinecraft.mc;

public final class CheckBox extends Component {
    private final BooleanSetting booleanSetting;

    public CheckBox(Module module, Setting setting, int offset) {
        super(module, setting, offset);
        this.booleanSetting = (BooleanSetting) setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.fontRendererObj.drawStringWithShadow(this.booleanSetting.getName() + ": ", 40 + 102 + 4, 62 + 4 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), Color.lightGray.getRGB());
        mc.fontRendererObj.drawStringWithShadow(String.valueOf(this.booleanSetting.isEnabled()), 40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.booleanSetting.getName() + ": "), 62 + 4 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), this.booleanSetting.isEnabled() ? Color.green.getRGB() : Color.red.getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.isRectHovered(mouseX, mouseY, 40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.booleanSetting.getName() + ": "), 62 + 4 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), mc.fontRendererObj.getStringWidth(String.valueOf(this.booleanSetting.isEnabled())), mc.fontRendererObj.FONT_HEIGHT)) {
                this.booleanSetting.setEnabled(!this.booleanSetting.isEnabled());
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    private boolean isRectHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
