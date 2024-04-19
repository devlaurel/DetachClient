package dev.laurel.module;

import dev.laurel.client.Client;
import dev.laurel.client.setting.Setting;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class Module {
    private final String name, description;
    private final ModuleCategory moduleCategory;

    @Setter private String suffix;

    @Setter private int key;

    private boolean enabled;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (this.enabled) this.onEnable();
        else this.onDisable();
    }

    public void toggle() {
        this.setEnabled(!this.enabled);
    }

    private final List<Setting> settings;

    protected void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public Module() {
        ModuleInfo moduleInfo = this.getClass().getAnnotation(ModuleInfo.class);
        this.name = moduleInfo.name();
        this.description = moduleInfo.description();
        this.suffix = "";
        this.moduleCategory = moduleInfo.moduleCategory();
        this.settings = new ArrayList<>();
    }

    protected void onEnable() {
        Client.INSTANCE.getEventBus().subscribe(this);
    }

    protected void onDisable() {
        Client.INSTANCE.getEventBus().unsubscribe(this);
    }
}
