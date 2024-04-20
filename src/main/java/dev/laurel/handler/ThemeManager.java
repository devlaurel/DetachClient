package dev.laurel.handler;

import lombok.Getter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ThemeManager {
    public static final ThemeManager INSTANCE = new ThemeManager();

    private final Map<String, Color[]> colorThemes = new HashMap<>();

    public Color[] getColorTheme(String colorThemeName) {
        return this.colorThemes.get(colorThemeName);
    }

    public ThemeManager() {
        this.colorThemes.put("DeepSea", new Color[]{new Color(155, 175, 217), new Color(16, 55, 131)});
        this.colorThemes.put("PornHub", new Color[]{new Color(255,163,26), new Color(27, 27, 27)});
        this.colorThemes.put("Tenacity", new Color[]{new Color(64, 201, 254), new Color(232, 28, 254)});
        this.colorThemes.put("Aqua", new Color[]{new Color(0, 255, 135), new Color(96, 239, 255)});
        this.colorThemes.put("Navy", new Color[]{new Color(13, 59, 102), new Color(250, 240, 202)});
    }
}
