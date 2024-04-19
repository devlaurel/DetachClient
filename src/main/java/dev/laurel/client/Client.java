package dev.laurel.client;

import dev.codeman.eventbus.EventBus;
import dev.laurel.handler.KeyHandler;
import dev.laurel.handler.RotationHandler;
import dev.laurel.module.ModuleManager;
import lombok.Getter;
import net.minecraft.util.Session;

import static dev.laurel.client.IMinecraft.mc;

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
        this.eventBus.subscribe(RotationHandler.INSTANCE);

        String username = "laurelxd";
        String uuid = "d8ab8bc045d64b828dc7570805db5842";
        String token = "eyJraWQiOiJhYzg0YSIsImFsZyI6IkhTMjU2In0.eyJ4dWlkIjoiMjUzNTQ0MzA5MDE3Nzc0MyIsImFnZyI6IkFkdWx0Iiwic3ViIjoiN2Q1NmIxMzgtNDJmNi00ZTFiLWJkYWYtZTZjNGQwYzg1Y2Y0IiwiYXV0aCI6IlhCT1giLCJucyI6ImRlZmF1bHQiLCJyb2xlcyI6W10sImlzcyI6ImF1dGhlbnRpY2F0aW9uIiwiZmxhZ3MiOlsidHdvZmFjdG9yYXV0aCIsIm1zYW1pZ3JhdGlvbl9zdGFnZTQiLCJvcmRlcnNfMjAyMiIsIm11bHRpcGxheWVyIl0sInByb2ZpbGVzIjp7Im1jIjoiZDhhYjhiYzAtNDVkNi00YjgyLThkYzctNTcwODA1ZGI1ODQyIn0sInBsYXRmb3JtIjoiUENfTEFVTkNIRVIiLCJ5dWlkIjoiYmYzOTllODhlMWRhOTIwY2VhYmVhZGU3MDIyZDc1MTUiLCJuYmYiOjE3MTM1MzE5MDMsImV4cCI6MTcxMzYxODMwMywiaWF0IjoxNzEzNTMxOTAzfQ.auGVTY7-hvCFaRCqSeeF-QFf8jF5kxB1FDroylF88sk";
        //mc.setSession(new Session(username, uuid, token, "microsoft"));
    }

    public void shutdown() {
        System.out.println("Shutting down " + CLIENT_NAME + " " + CLIENT_BUILD + " by " + CLIENT_AUTHOR + "...");
    }
}
