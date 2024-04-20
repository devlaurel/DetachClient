package dev.laurel.module.impl.player;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.setting.impl.BooleanSetting;
import dev.laurel.client.setting.impl.NumberSetting;
import dev.laurel.event.EventRender2D;
import dev.laurel.event.EventUpdate;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import dev.laurel.util.TimeHelper;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;

import java.util.Random;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "ChestStealer", description = "Automatically steals items from chests", moduleCategory =  ModuleCategory.PLAYER)
public final class ChestStealerModule extends Module {

    private final TimeHelper timeHelper = new TimeHelper();
    private final Random random = new Random();

    private final NumberSetting stealDelay = new NumberSetting("StealDelay", 100, 0, 1000, 50);
    private final BooleanSetting randomizeSlots = new BooleanSetting("RandomizeSlots", false);
    private final BooleanSetting autoClose = new BooleanSetting("AutoClose", true);
    private final BooleanSetting checkTitle = new BooleanSetting("CheckTitle", false);

    public ChestStealerModule() {
        this.addSettings(this.stealDelay, this.randomizeSlots, this.autoClose, this.checkTitle);
    }

    @EventHandler
    private final Listener<EventRender2D> eventRender2DListener = event -> this.setSuffix(String.valueOf(this.stealDelay.getValue()));

    @EventHandler
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        if (!(mc.currentScreen instanceof GuiChest)) return;
        ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;

        for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
            if (chest.getLowerChestInventory().getStackInSlot(i) == null) continue;

            if (this.checkTitle.isEnabled() && !chest.getLowerChestInventory().getDisplayName().getUnformattedText().equals("Chest")) return;

            if (this.timeHelper.hasPassed((long) this.stealDelay.getValue())) {
                int slot = this.randomizeSlots.isEnabled() ? this.random.nextInt(chest.getLowerChestInventory().getSizeInventory()) : i;
                mc.playerController.windowClick(chest.windowId, slot, 0, 1, mc.thePlayer);
                if (this.autoClose.isEnabled() && this.isChestEmpty(chest)) mc.thePlayer.closeScreen();
                this.timeHelper.reset();
            }
        }
    };

    private boolean isChestEmpty(ContainerChest chest) {
        for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
            if (chest.getLowerChestInventory().getStackInSlot(i) != null) return false;
        }
        return true;
    }
}
