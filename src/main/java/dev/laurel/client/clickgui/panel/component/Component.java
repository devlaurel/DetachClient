package dev.laurel.client.clickgui.panel.component;

import dev.laurel.client.setting.Setting;
import dev.laurel.module.Module;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Component {
    private final Module module;
    private final Setting setting;
    private final int offset;

    public abstract void drawScreen(int mouseX, int mouseY, float partialTicks);

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);

    public abstract void mouseReleased(int mouseX, int mouseY, int state);
}
