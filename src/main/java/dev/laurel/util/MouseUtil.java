package dev.laurel.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MouseUtil {

    public boolean isRectHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
