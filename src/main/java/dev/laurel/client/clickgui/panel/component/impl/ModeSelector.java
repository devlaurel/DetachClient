package dev.laurel.client.clickgui.panel.component.impl;

import dev.laurel.client.clickgui.panel.component.Component;
import dev.laurel.client.setting.Setting;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.module.Module;

import java.awt.*;
import java.util.Objects;

import static dev.laurel.client.IMinecraft.mc;

public final class ModeSelector extends Component {
    private final ModeSetting modeSetting;

    public ModeSelector(Module module, Setting setting, int offset) {
        super(module, setting, offset);
        this.modeSetting = (ModeSetting) setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.fontRendererObj.drawStringWithShadow(this.modeSetting.getName() + ": ", 40 + 102 + 4, 62 + 4 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), Color.lightGray.getRGB());
        int count = 0;
        for (String mode : this.modeSetting.getModes()) {
            mc.fontRendererObj.drawStringWithShadow(mode, 40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.modeSetting.getName() + ": ") + count, 62 + 4 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), Objects.equals(mode, this.modeSetting.getCurrentMode()) ? new Color(100, 150, 200).getRGB() : Color.lightGray.getRGB());
            mc.fontRendererObj.drawStringWithShadow(",", 40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.modeSetting.getName() + ": " + mode) + count, 62 + 4 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), Color.lightGray.getRGB());
            count += mc.fontRendererObj.getStringWidth(mode) + 6;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            int count = 0;
            for (String mode : this.modeSetting.getModes()) {
                if (this.isRectHovered(mouseX, mouseY, 40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.modeSetting.getName() + ": ") + count, 62 + 4 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), mc.fontRendererObj.getStringWidth(mode), mc.fontRendererObj.FONT_HEIGHT)) {
                    this.modeSetting.setCurrentMode(mode);
                }
                count += mc.fontRendererObj.getStringWidth(mode) + 6;
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
