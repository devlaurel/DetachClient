package dev.laurel.module.impl.visual;

import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import lombok.Getter;

@Getter
@ModuleInfo(name = "Scoreboard", description = "Allows you to modify the scoreboard", moduleCategory = ModuleCategory.VISUAL)
public final class ScoreboardModule extends Module {

    private final BooleanSetting hideScoreboard = new BooleanSetting("HideScoreboard", false);

    public ScoreboardModule() {
        this.addSettings(this.hideScoreboard);
    }
}
