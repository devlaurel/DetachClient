package dev.laurel.client.setting.impl;

import dev.laurel.client.setting.Setting;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class BooleanSetting extends Setting {
    private boolean enabled;

    public BooleanSetting(String name, boolean defaultState) {
        super(name);
        this.enabled = defaultState;
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }
}
