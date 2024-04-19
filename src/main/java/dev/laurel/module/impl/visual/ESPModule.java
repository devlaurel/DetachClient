package dev.laurel.module.impl.visual;

import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import lombok.Getter;

@Getter
@ModuleInfo(name = "ESP", description = "Renders entities through walls", moduleCategory = ModuleCategory.VISUAL)
public final class ESPModule extends Module {

    private final ModeSetting mode = new ModeSetting("Mode", "Outline", "Shader", "Real2D");

    public ESPModule() {
        this.addSettings(this.mode);
    }
}
