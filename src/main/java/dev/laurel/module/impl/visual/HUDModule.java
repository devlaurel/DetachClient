package dev.laurel.module.impl.visual;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.Client;
import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.handler.ThemeManager;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import dev.laurel.util.render.ColorUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "HUD", description = "Displays the client's HUD", moduleCategory = ModuleCategory.VISUAL)
public final class HUDModule extends Module {

    private final NumberSetting textBackgroundOpacity = new NumberSetting("TextBackgroundOpacity", 100, 0, 255, 5);
    private final BooleanSetting hideVisualModules = new BooleanSetting("HideVisualModules", false);
    private final NumberSetting cornerOffset = new NumberSetting("CornerOffset", 4, 0, 20, 1);
    private final ModeSetting colorTheme = new ModeSetting("ColorTheme", "DeepSea", "PornHub", "Tenacity", "Aqua", "Navy");
    private final NumberSetting colorOffset = new NumberSetting("ColorOffset", 1, 0, 4, 0.1);

    public HUDModule() {
        this.setEnabled(true);
        this.addSettings(this.textBackgroundOpacity,this.hideVisualModules, this.cornerOffset, this.colorTheme, this.colorOffset);
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> {
        this.setSuffix(String.valueOf(this.colorTheme.getCurrentMode()));

        final ScaledResolution scaledResolution = event.getScaledResolution();

        final List<Module> modules = new ArrayList<>(Client.INSTANCE.getModuleManager().getModules().values());
        modules.sort(Comparator.comparingInt(module -> -mc.fontRendererObj.getStringWidth(module.getName() + (Objects.equals(module.getSuffix(), "") ? "" : " " + module.getSuffix()))));

        int count = 0;
        for (Module module : modules) {
            if (!module.isEnabled()) continue;
            final boolean hasModuleSuffix = !Objects.equals(module.getSuffix(), "");

            Color[] themeColors = ThemeManager.INSTANCE.getColorTheme(colorTheme.getCurrentMode());
            Color color = ColorUtil.blend(themeColors[0], themeColors[1], (float) (Math.sin((System.currentTimeMillis() / 250D) - count * colorOffset.getValue()) + 1D) / 2F);

            if (this.hideVisualModules.isEnabled() && module.getModuleCategory() == ModuleCategory.VISUAL) continue;
            Gui.drawRect((int) (scaledResolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getName() + (hasModuleSuffix ? " " + module.getSuffix() : "")) - 4 - this.cornerOffset.getValue()), (int) (count * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.cornerOffset.getValue()), (int) (scaledResolution.getScaledWidth() - this.cornerOffset.getValue()), (int) (count * (mc.fontRendererObj.FONT_HEIGHT + 2) + mc.fontRendererObj.FONT_HEIGHT + 2 + this.cornerOffset.getValue()), new Color(0, 0, 0, (int) textBackgroundOpacity.getValue()).getRGB());

            mc.fontRendererObj.drawStringWithShadow(module.getName(), (float) (scaledResolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getName() + (hasModuleSuffix ? " " + module.getSuffix() : "")) - 2 - this.cornerOffset.getValue()), (float) (2 + count * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.cornerOffset.getValue()), color.getRGB());
            mc.fontRendererObj.drawStringWithShadow(module.getSuffix(), (float) ((float) (scaledResolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getSuffix()) - 2) - this.cornerOffset.getValue()), (float) (2 + count * (mc.fontRendererObj.FONT_HEIGHT + 2) + this.cornerOffset.getValue()), Color.lightGray.getRGB());
            count++;
        }
    };
}
