package dev.laurel.client.clickgui.panel;

import dev.laurel.client.Client;
import dev.laurel.client.clickgui.panel.component.Component;
import dev.laurel.client.clickgui.panel.component.impl.CheckBox;
import dev.laurel.client.clickgui.panel.component.impl.ModeSelector;
import dev.laurel.client.clickgui.panel.component.impl.Slider;
import dev.laurel.client.setting.Setting;
import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.impl.motion.SprintModule;
import dev.laurel.util.DrawUtil;
import lombok.Getter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public final class PanelClickGUI extends GuiScreen {
    public static final PanelClickGUI INSTANCE = new PanelClickGUI();

    private final float scale = 1F;

    private ModuleCategory selectedModuleCategory;
    private Module selectedModule;

    private final List<Component> components;

    public PanelClickGUI() {
        this.selectedModuleCategory = ModuleCategory.MOVEMENT;
        this.selectedModule = Client.INSTANCE.getModuleManager().getModule(SprintModule.class);
        components = new ArrayList<>();

        int offset = 0;
        for (Module module : Client.INSTANCE.getModuleManager().getModules().values()) {
            for (Setting setting : module.getSettings()) {
                if (setting instanceof NumberSetting) {
                    components.add(new Slider(module, setting, offset));
                } else if (setting instanceof BooleanSetting) {
                    components.add(new CheckBox(module, setting, offset));
                } else if (setting instanceof ModeSetting) {
                    components.add(new ModeSelector(module, setting, offset));
                }
                offset++;
            }
            offset = 0;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        GlStateManager.pushMatrix();
        GlStateManager.scale(this.scale, this.scale, 1F);
        // Title bar
        DrawUtil.drawFilledRect(40, 28, 400, 12, new Color(32, 32, 32));
        mc.fontRendererObj.drawStringWithShadow("ClickGUI", 42, 30, Color.lightGray.getRGB());
        // Panel
        DrawUtil.drawFilledRect(40, 40, 400, 280, new Color(0, 0, 0, 120));
        // Vertical line
        DrawUtil.drawFilledRect(40 + 100, 40, 2, 280, new Color(32, 32, 32));
        // Horizontal line
        DrawUtil.drawFilledRect(40 + 100 , 60, 300, 2, new Color(32, 32, 32));

        int count = 0;
        for (ModuleCategory moduleCategory : ModuleCategory.values()) {
            mc.fontRendererObj.drawStringWithShadow(moduleCategory.getName(), 40 + 102 + 8 + count, 40 + 6, moduleCategory == this.selectedModuleCategory ? new Color(100, 150, 200).getRGB() : Color.lightGray.getRGB());
            // DrawUtil.drawFilledRect(40 + 102 + 8 + count, 40 + mc.fontRendererObj.FONT_HEIGHT + 6, mc.fontRendererObj.getStringWidth(moduleCategory.getName()), 2, Color.lightGray);
            count += mc.fontRendererObj.getStringWidth(moduleCategory.getName()) + 8;
        }

        int count2 = 0;
        for (Module module : Client.INSTANCE.getModuleManager().getModulesInCategory(this.selectedModuleCategory)) {
            mc.fontRendererObj.drawStringWithShadow(module == this.selectedModule ? "> " + module.getName() : module.getName(), 40 + 6, 40 + 6 + count2 * (mc.fontRendererObj.FONT_HEIGHT + 2), module.isEnabled() ? new Color(100, 150, 200).getRGB() : Color.lightGray.getRGB());
            count2++;
        }

        mc.fontRendererObj.drawStringWithShadow(this.selectedModule.getName() + ":", 40 + 102 + 4, 62 + 4, new Color(100, 150, 200).getRGB());
        mc.fontRendererObj.drawStringWithShadow(this.selectedModule.getDescription(), 40 + 102 + 4 + mc.fontRendererObj.getStringWidth(this.selectedModule.getName() + ": "), 62 + 4, Color.gray.getRGB());
        mc.fontRendererObj.drawStringWithShadow("Key: " + Keyboard.getKeyName(this.selectedModule.getKey()), 40 + 102 + 4, 62 + 4 + (mc.fontRendererObj.FONT_HEIGHT + 2), Color.lightGray.getRGB());

        mc.fontRendererObj.drawStringWithShadow("Settings: ", 40 + 102 + 4, 62 + 4 + (mc.fontRendererObj.FONT_HEIGHT + 2) * 3, new Color(240, 160, 0).getRGB());

        GlStateManager.popMatrix();

        for (Component component : this.components) {
            if (component.getModule() == this.selectedModule) {
                component.drawScreen(mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0) {

            int count = 0;
            for (ModuleCategory moduleCategory : ModuleCategory.values()) {
                if (this.isRectHovered(mouseX, mouseY, 40 + 102 + 6 + count, 40 + 6, mc.fontRendererObj.getStringWidth(moduleCategory.getName()), mc.fontRendererObj.FONT_HEIGHT)) {
                    this.selectedModuleCategory = moduleCategory;
                }
                count += mc.fontRendererObj.getStringWidth(moduleCategory.getName()) + 8;
            }

            int count2 = 0;
            for (Module module : Client.INSTANCE.getModuleManager().getModulesInCategory(this.selectedModuleCategory)) {
                if (this.isRectHovered(mouseX, mouseY, 46, 46 + count2 * (mc.fontRendererObj.FONT_HEIGHT + 2), module == this.selectedModule ? mc.fontRendererObj.getStringWidth("> " + module.getName()) : mc.fontRendererObj.getStringWidth(module.getName()), mc.fontRendererObj.FONT_HEIGHT)) {
                    module.toggle();
                }
                count2++;
            }
        } else if (mouseButton == 1) {

            int count2 = 0;
            for (Module module : Client.INSTANCE.getModuleManager().getModulesInCategory(this.selectedModuleCategory)) {
                if (this.isRectHovered(mouseX, mouseY, 46, 46 + count2 * (mc.fontRendererObj.FONT_HEIGHT + 2), mc.fontRendererObj.getStringWidth(module.getName()), mc.fontRendererObj.FONT_HEIGHT)) {
                    this.selectedModule = module;
                }
                count2++;
            }
        }

        for (Component component : this.components) {
            if (component.getModule() == this.selectedModule) {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        for (Component component : this.components) {
            if (component.getModule() == this.selectedModule) {
                component.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private boolean isRectHovered(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x * this.scale && mouseX <= (x + width) * this.scale && mouseY >= y * this.scale && mouseY <= (y + height) * this.scale;
    }
}
