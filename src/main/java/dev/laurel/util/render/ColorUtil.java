package dev.laurel.util.render;

import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public final class ColorUtil {

    public Color blend(Color color1, Color color2, float ratio) {
        float inverseRatio = 1F - ratio;
        float red = color1.getRed() * ratio + color2.getRed() * inverseRatio;
        float green = color1.getGreen() * ratio + color2.getGreen() * inverseRatio;
        float blue = color1.getBlue() * ratio + color2.getBlue() * inverseRatio;
        return new Color(red / 255F, green / 255F, blue / 255F);
    }
}
