package dev.laurel.client.clickgui.panel.component.impl;

import dev.laurel.client.clickgui.panel.PanelClickGUI;
import dev.laurel.client.clickgui.panel.component.Component;
import dev.laurel.client.setting.Setting;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.module.Module;
import dev.laurel.util.DrawUtil;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static dev.laurel.client.IMinecraft.mc;

public final class Slider extends Component {
    private final NumberSetting numberSetting;

    private boolean sliding = false;

    public Slider(Module module, Setting setting, int offset) {
        super(module, setting, offset);
        this.numberSetting = (NumberSetting) setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.fontRendererObj.drawStringWithShadow(this.numberSetting.getName() + ": ", 40 + 102 + 4, 62 + 4 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), Color.lightGray.getRGB());

        // Box
        DrawUtil.drawFilledRect(40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.numberSetting.getName() + ": "), 62 + 2 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), 120, 1, new Color(32, 32, 32));
        DrawUtil.drawFilledRect(40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.numberSetting.getName() + ": "), 62 + 12 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), 120, 1, new Color(32, 32, 32));
        DrawUtil.drawFilledRect(40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.numberSetting.getName() + ": "), 62 + 2 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), 1, mc.fontRendererObj.FONT_HEIGHT + 2, new Color(32, 32, 32));
        DrawUtil.drawFilledRect(40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.numberSetting.getName() + ": ") + 120, 62 + 2 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), 1, mc.fontRendererObj.FONT_HEIGHT + 2, new Color(32, 32, 32));

        double diff = Math.min(119, Math.max(0, mouseX - (40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.numberSetting.getName() + ": ") + 1)));
        int renderWidth = (int) (119 * (this.numberSetting.getValue() - this.numberSetting.getMin()) / (this.numberSetting.getMax() - this.numberSetting.getMin()));
        DrawUtil.drawFilledRect(40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.numberSetting.getName() + ": ") + 1, 62 + 3 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), renderWidth, mc.fontRendererObj.FONT_HEIGHT, new Color(100, 150, 200));

        if (this.sliding) {
            if (diff == 0) {
                this.numberSetting.setValue(this.numberSetting.getMin());
            } else {
                this.numberSetting.setValue(this.roundToPlace(((diff / 119) * (this.numberSetting.getMax() - this.numberSetting.getMin()) + this.numberSetting.getMin()), 2));
            }
        }

        mc.fontRendererObj.drawStringWithShadow(String.valueOf(this.numberSetting.getValue()), 40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.numberSetting.getName() + ": ") + 120 / 2F - (mc.fontRendererObj.getStringWidth(String.valueOf(this.numberSetting.getValue())) / 2F), 62 + 4 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), Color.lightGray.getRGB());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (this.isRectHovered(mouseX, mouseY, 40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.numberSetting.getName() + ": ") + 1, 62 + 3 + 4 * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.getOffset() * (mc.fontRendererObj.FONT_HEIGHT + 4), 119, mc.fontRendererObj.FONT_HEIGHT)) {
                this.sliding = true;
                System.out.println("Sliding!");
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            if (this.sliding) {
                this.sliding = false;
            }
        }
    }

    private boolean isRectHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    private double roundToPlace(double value, int place) {
        if (place < 0) return value;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(place, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
