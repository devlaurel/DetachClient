package dev.laurel.util;

import lombok.experimental.UtilityClass;
import net.minecraft.client.gui.Gui;

import java.awt.*;

@UtilityClass
public class DrawUtil {

    public void drawFilledRect(int x, int y, int width, int height, Color color) {
        Gui.drawRect(x, y, x + width, y + height, color.getRGB());
    }
}
