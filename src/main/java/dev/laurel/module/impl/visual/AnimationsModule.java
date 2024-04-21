package dev.laurel.module.impl.visual;

import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import lombok.Getter;

@Getter
@ModuleInfo(name = "Animations", description = "Modifies your block animations", moduleCategory = ModuleCategory.VISUAL)
public final class AnimationsModule extends Module {

    private final BooleanSetting noTranslate = new BooleanSetting("NoTranslate", true);
    private final NumberSetting itemScale = new NumberSetting("ItemScale", 0.75, 0.5, 2, 0.05);

    public AnimationsModule() {
        this.addSettings(this.noTranslate, this.itemScale);
    }
}
