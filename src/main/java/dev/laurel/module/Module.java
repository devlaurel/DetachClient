package dev.laurel.module;

import dev.laurel.client.Client;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Module {
    private final String name, description;
    private final ModuleCategory moduleCategory;

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

    public Module() {
        ModuleInfo moduleInfo = this.getClass().getAnnotation(ModuleInfo.class);
        this.name = moduleInfo.name();
        this.description = moduleInfo.description();
        this.moduleCategory = moduleInfo.moduleCategory();
    }

    protected void onEnable() {
        Client.INSTANCE.getEventBus().subscribe(this);
    }

    protected void onDisable() {
        Client.INSTANCE.getEventBus().unsubscribe(this);
    }
}
