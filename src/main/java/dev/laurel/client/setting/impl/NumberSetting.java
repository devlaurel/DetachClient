package dev.laurel.client.setting.impl;

import dev.laurel.client.setting.Setting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class NumberSetting extends Setting {
    private double value;
    private final double min, max, increment;

    public NumberSetting(String name, double defaultValue, double min, double max, double increment) {
        super(name);
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    private double clamp(double value) {
        return Math.min(max, Math.max(min, value));
    }
}
