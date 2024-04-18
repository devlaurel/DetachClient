package dev.laurel.client.setting.impl;

import dev.laurel.client.setting.Setting;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public final class ModeSetting extends Setting {
    private final List<String> modes;

    private int modeIndex;
    private String currentMode;

    public ModeSetting (String name, String... modes) {
        super(name);
        this.modes = Arrays.asList(modes);
        this.modeIndex = this.modes.indexOf(modes[0]);
        if (currentMode == null) currentMode = modes[0];
    }

    private void cycleForwards() {
        modeIndex++;
        if (modeIndex > modes.size() - 1) modeIndex = 0;
        currentMode = modes.get(modeIndex);
    }

    private void cycleBackwards() {
        modeIndex--;
        if (modeIndex < 0) modeIndex = modes.size() - 1;
        currentMode = modes.get(modeIndex);
    }
}
