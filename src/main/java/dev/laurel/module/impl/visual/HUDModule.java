package dev.laurel.module.impl.visual;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.Client;
import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import dev.laurel.util.ColorUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "HUD", description = "Displays the client's HUD", moduleCategory = ModuleCategory.VISUAL)
public final class HUDModule extends Module {

    private final BooleanSetting textBackground = new BooleanSetting("TextBackground", true);
    private final BooleanSetting hideVisualModules = new BooleanSetting("HideVisualModules", false);

    public HUDModule() {
        this.setEnabled(true);
        this.addSettings(this.textBackground, this.hideVisualModules);
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> {
        final ScaledResolution scaledResolution = event.getScaledResolution();

        ArrayList<Module> modules = new ArrayList<>(Client.INSTANCE.getModuleManager().getModules().values());
        modules.sort(Comparator.comparingInt(module -> -mc.fontRendererObj.getStringWidth(module.getName())));

        int count = 0;
        for (Module module : modules) {
            if (!module.isEnabled()) continue;
            if (this.hideVisualModules.isEnabled() && module.getModuleCategory() == ModuleCategory.VISUAL) continue;
            if (this.textBackground.isEnabled()) {
                Gui.drawRect(scaledResolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getName()) - 4, count * (mc.fontRendererObj.FONT_HEIGHT + 2), scaledResolution.getScaledWidth(), count * (mc.fontRendererObj.FONT_HEIGHT + 2) + mc.fontRendererObj.FONT_HEIGHT + 2, new Color(0, 0, 0, 80).getRGB());
            }
            int color = ColorUtil.blend(new Color(13, 59, 102), new Color(250, 240, 202), (float) (Math.sin((System.currentTimeMillis() / 500D) % 1000 - count) + 1D) / 2F).getRGB();
            mc.fontRendererObj.drawStringWithShadow(module.getName(), scaledResolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getName()) - 2, 2 + count * (mc.fontRendererObj.FONT_HEIGHT + 2), color);
            count++;
        }
    };
}
