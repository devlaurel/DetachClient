package dev.laurel.util.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtil {

    private static final Minecraft MC = Minecraft.getMinecraft();

    public enum MessageType {
        INFO,
        WARNING,
        ERROR
    }

    private MessageType messageType;

    public static void addChatMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public static void addChatMessage(String message, MessageType messageType) {
        switch (messageType) {
            case INFO:
                MC.thePlayer.addChatMessage(new ChatComponentText("§1[§9Info§1]§7 " + message));
                break;
            case WARNING:
                MC.thePlayer.addChatMessage(new ChatComponentText("§e[§6Warning§e]§7 " + message));
                break;
            case ERROR:
                MC.thePlayer.addChatMessage(new ChatComponentText("§4[§cError§4]§7 " + message));
                break;
        }
    }
}
