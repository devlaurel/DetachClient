package dev.laurel.module.impl.visual;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.Client;
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
public class HUDModule extends Module {

    public HUDModule() {
        this.setEnabled(true);
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> {
        final ScaledResolution scaledResolution = event.getScaledResolution();

        ArrayList<Module> modules = new ArrayList<>(Client.INSTANCE.getModuleManager().getModules().values());
        modules.sort(Comparator.comparingInt(module -> -mc.fontRendererObj.getStringWidth(module.getName())));

        int count = 0;
        for (Module module : modules) {
            if (!module.isEnabled()) continue;
            Gui.drawRect(scaledResolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getName()) - 4, count * (mc.fontRendererObj.FONT_HEIGHT + 2), scaledResolution.getScaledWidth(), count * (mc.fontRendererObj.FONT_HEIGHT + 2) + mc.fontRendererObj.FONT_HEIGHT + 2, new Color(0, 0, 0, 80).getRGB());
            int color = ColorUtil.blend(new Color(8, 65, 92), new Color(204, 41, 54), (float) ((Math.sin((System.currentTimeMillis() / 500D) % 1000 - count) + 1D) / 2F)).getRGB();
            mc.fontRendererObj.drawStringWithShadow(module.getName(), scaledResolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getName()) - 2, 2 + count * (mc.fontRendererObj.FONT_HEIGHT + 2), color);
            count++;
        }
    };
}
