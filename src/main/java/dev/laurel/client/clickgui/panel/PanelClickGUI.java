package dev.laurel.client.clickgui.panel;

import dev.laurel.client.Client;
import dev.laurel.client.clickgui.panel.component.Component;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.impl.motion.SprintModule;
import dev.laurel.util.DrawUtil;
import dev.laurel.util.MouseUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class PanelClickGUI extends GuiScreen {
    public static final PanelClickGUI INSTANCE = new PanelClickGUI();

    private float scale = 0.8F;

    private int guiX = 40;
    private int guiY = 40;
    private int guiWidth = 360;
    private int guiHeight = 280;

    private int lineX = guiX + 40;

    private boolean dragging = false;
    private int dragX;
    private int dragY;

    private ModuleCategory selectedModuleCategory;
    private Module selectedModule;

    private final List<Component> components;

    private boolean isSelectedModuleCategory(ModuleCategory moduleCategory) {
        return this.selectedModuleCategory == moduleCategory;
    }

    private boolean isSelectedModule(Module module) {
        return this.selectedModule == module;
    }

    public PanelClickGUI() {
        this.selectedModuleCategory = ModuleCategory.values()[0];
        this.selectedModule = Client.INSTANCE.getModuleManager().getModule(SprintModule.class);
        this.components = new ArrayList<>();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        GlStateManager.pushMatrix();
        GlStateManager.scale(this.scale, this.scale, 1F);

        // Panel
        DrawUtil.drawFilledRect(this.guiX, this.guiY, this.guiWidth, this.guiHeight, new Color(2, 22, 98));

        // Line
        DrawUtil.drawFilledRect(this.guiX + this.lineX, this.guiY, 2, this.guiHeight, new Color(2, 62, 138));

        // Title bar
        DrawUtil.drawFilledRect(this.guiX, this.guiY - 12, this.guiWidth, 12, new Color(2, 62, 138));
        mc.fontRendererObj.drawStringWithShadow("Windows PowerShell ISE (x86)", this.guiX + 2, this.guiY - 10, Color.white.getRGB());

        int count = 0;
        for (Module module : Client.INSTANCE.getModuleManager().getModulesInCategory(this.selectedModuleCategory)) {
            mc.fontRendererObj.drawStringWithShadow(module.getName(), this.guiX + 2, this.guiY + 2 + count * (mc.fontRendererObj.FONT_HEIGHT + 2), this.isSelectedModule(module) ? new Color(2, 102, 178).getRGB() : Color.lightGray.getRGB());
            count++;
        }

        int count1 = 0;
        for (ModuleCategory moduleCategory : ModuleCategory.values()) {
            mc.fontRendererObj.drawStringWithShadow(moduleCategory.getName(), this.guiX + lineX + 4 + count1, this.guiY + 2, this.isSelectedModuleCategory(moduleCategory) ? new Color(2, 102, 178).getRGB() : Color.lightGray.getRGB());
            count1 += mc.fontRendererObj.getStringWidth(moduleCategory.getName()) + 8;
        }

        GlStateManager.popMatrix();

        for (Component component : this.components) {
            component.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0) {
            int count = 0;
            for (Module module : Client.INSTANCE.getModuleManager().getModulesInCategory(this.selectedModuleCategory)) {
                if (MouseUtil.isRectHovered(mouseX, mouseY, (int) ((this.guiX + 2) * this.scale), (int) ((this.guiY + 2 + count * (mc.fontRendererObj.FONT_HEIGHT + 2)) * this.scale), (int) ((this.lineX) * this.scale), (int) ((count * (mc.fontRendererObj.FONT_HEIGHT + 2) + mc.fontRendererObj.FONT_HEIGHT + 2) * this.scale))) {
                    this.selectedModule = module;
                }
                count++;
            }

            int count1 = 0;
            for (ModuleCategory moduleCategory : ModuleCategory.values()) {
                if (MouseUtil.isRectHovered(mouseX, mouseY, (int) ((this.guiX + lineX + 4 + count1) * this.scale), (int) ((this.guiY + 2) * this.scale), (int) ((mc.fontRendererObj.getStringWidth(moduleCategory.getName()) + 8) * this.scale), (int) ((mc.fontRendererObj.FONT_HEIGHT + 2) * this.scale))) {
                    this.selectedModuleCategory = moduleCategory;
                }
                count1 += mc.fontRendererObj.getStringWidth(moduleCategory.getName()) + 8;
            }
        }

        for (Component component : this.components) {
            component.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        for (Component component : this.components) {
            component.mouseReleased(mouseX, mouseY, state);
        }
    }
}
