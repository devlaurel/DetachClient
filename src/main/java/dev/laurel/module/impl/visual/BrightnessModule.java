package dev.laurel.module.impl.visual;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.ModeSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "Brightness", description = "Renders the game in full brightness", moduleCategory = ModuleCategory.VISUAL)
public final class BrightnessModule extends Module {

    private float oldGamma = 0F;

    private final ModeSetting mode = new ModeSetting("Mode", "Gamma", "FakeNightVision");

    public BrightnessModule() {
        this.addSettings(this.mode);
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> this.setSuffix(String.valueOf(this.mode.getCurrentMode()));

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        switch (this.mode.getCurrentMode()) {
            case "Gamma":
                mc.thePlayer.removePotionEffect(Potion.nightVision.id);
                mc.gameSettings.gammaSetting = 100F;
                break;
            case "FakeNightVision":
                mc.gameSettings.gammaSetting = 0F;
                mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 2401, 0, true, false));
        }
    };

    @Override
    protected void onEnable() {
        super.onEnable();
        this.oldGamma = mc.gameSettings.gammaSetting;
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = this.oldGamma;
    }
}
