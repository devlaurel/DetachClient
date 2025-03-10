package dev.laurel.handler;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.client.Client;
import dev.laurel.client.clickgui.panel.PanelClickGUI;
import dev.laurel.event.EventKey;
import dev.laurel.module.Module;
import org.lwjgl.input.Keyboard;

import static dev.laurel.client.IMinecraft.mc;

public final class KeyHandler {
    @EventHandler
    private final Listener<EventKey> eventKeyListener = event -> {
        final int key = event.getKey();
        for (Module module : Client.INSTANCE.getModuleManager().getModules().values()) {
            if (key == module.getKey()) module.toggle();
        }
    };
}
