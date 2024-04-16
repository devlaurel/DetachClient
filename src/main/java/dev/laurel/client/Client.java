package dev.laurel.client;

import dev.codeman.eventbus.EventBus;
import dev.laurel.handler.KeyHandler;
import dev.laurel.module.ModuleManager;
import lombok.Getter;

@Getter
public final class Client {
    public static final Client INSTANCE = new Client();

    private final String
    CLIENT_NAME = "Detach",
    CLIENT_BUILD = "b1.0.0",
    CLIENT_AUTHOR = "devlaurel";

    private EventBus eventBus;
    private ModuleManager moduleManager;
    private KeyHandler keyHandler;

    public void init() {
        this.eventBus = new EventBus();
        this.moduleManager = new ModuleManager();
        this.keyHandler = new KeyHandler();

        this.eventBus.subscribe(keyHandler);
    }

    public void shutdown() {
        System.out.println("Shutting down " + CLIENT_NAME + " " + CLIENT_BUILD + " by " + CLIENT_AUTHOR + "...");
    }
}
