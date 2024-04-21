package dev.laurel.module.impl.visual;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import lombok.Getter;

@Getter
@ModuleInfo(name = "ESP", description = "Renders entities through walls", moduleCategory = ModuleCategory.VISUAL)
public final class ESPModule extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Outline", "Shader", "Real2D");

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> this.setSuffix(String.valueOf(this.mode.getCurrentMode()));

    public ESPModule() {
        this.addSettings(this.mode);
    }
}
