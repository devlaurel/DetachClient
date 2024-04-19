package dev.laurel.module.impl.player;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import net.minecraft.item.ItemBlock;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "FastPlace", description = "Makes you place blocks faster", moduleCategory = ModuleCategory.PLAYER)
public final class FastPlaceModule extends Module {

    private final BooleanSetting onlyBlocks = new BooleanSetting("OnlyBlocks", true);

    public FastPlaceModule() {
        this.addSettings(this.onlyBlocks);
    }

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        if (this.onlyBlocks.isEnabled()) {
            if (mc.thePlayer.getHeldItem() == null) return;
            if (!(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) return;
            mc.setRightClickDelayTimer(0);
        }
        mc.setRightClickDelayTimer(0);
    };
}
