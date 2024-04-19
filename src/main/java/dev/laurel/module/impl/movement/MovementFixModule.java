package dev.laurel.module.impl.movement;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.event.EventJump;
import dev.laurel.event.EventMoveFlying;
import dev.laurel.event.EventMoveInput;
import dev.laurel.handler.RotationHandler;
import dev.laurel.module.Module;
import dev.laurel.module.ModuleCategory;
import dev.laurel.module.ModuleInfo;
import net.minecraft.util.MathHelper;

import static dev.laurel.client.IMinecraft.mc;

@ModuleInfo(name = "MovementFix", description = "Makes your movement legit", moduleCategory = ModuleCategory.MOVEMENT)
public final class MovementFixModule extends Module {

    @EventHandler
    private final Listener<EventMoveFlying> eventMoveFlyingListener = event -> event.setYaw(RotationHandler.INSTANCE.getServersideYaw());

    @EventHandler
    private final Listener<EventJump> eventJumpListener = event -> event.setYaw(RotationHandler.INSTANCE.getServersideYaw());

    @EventHandler
    private final Listener<EventMoveInput> eventMoveInputListener = event -> {
        float deltaYaw = (float) Math.toRadians(MathHelper.wrapAngleTo180_float(RotationHandler.INSTANCE.getServersideYaw() - mc.thePlayer.rotationYaw));

        float f1 = (float) Math.sin(deltaYaw),
                f2 = (float) Math.cos(deltaYaw);

        float forward = Math.round(event.getForward() * f2 - event.getStrafe() * f1),
                strafe = Math.round(event.getStrafe() * f2 + event.getForward() * f1);

        event.setForward(forward);
        event.setStrafe(strafe);
    };
}
