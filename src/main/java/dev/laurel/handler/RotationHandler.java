package dev.laurel.handler;

import dev.codeman.eventbus.EventHandler;
import dev.codeman.eventbus.Listener;
import dev.laurel.event.EventJump;
import dev.laurel.event.EventLook;
import dev.laurel.event.EventMotion;
import dev.laurel.event.EventUpdate;
import lombok.Getter;
import lombok.Setter;

import static dev.laurel.client.IMinecraft.mc;

@Getter
@Setter
public final class RotationHandler {
    public static final RotationHandler INSTANCE = new RotationHandler();

    private float serversideYaw;
    private float serversidePitch;

    private float prevServersideYaw;
    private float prevServersidePitch;

    @EventHandler(priority = -2)
    private final Listener<EventUpdate> eventUpdateListener = event -> {
        this.prevServersideYaw = this.serversideYaw;
        this.prevServersidePitch = this.serversidePitch;

        this.setServersideYaw(mc.thePlayer.rotationYaw);
        this.setServersidePitch(mc.thePlayer.rotationPitch);
    };

    @EventHandler
    private final Listener<EventMotion> eventMotionListener = event -> {
        float[] rotationTickDelta = new float[]{this.serversideYaw - this.prevServersideYaw, this.serversidePitch - this.prevServersidePitch};
        final float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        final float f1 = f * f * f * 8.0F * 0.15f;
        float fixedYaw = this.prevServersideYaw + rotationTickDelta[0] - rotationTickDelta[0] % f1;
        float fixedPitch = this.prevServersidePitch + rotationTickDelta[1] - rotationTickDelta[1] % f1;

        event.setYaw(fixedYaw);
        event.setPitch(fixedPitch);

        mc.thePlayer.rotationYawHead = fixedYaw;
        mc.thePlayer.renderYawOffset = fixedYaw;
        mc.thePlayer.rotationPitchHead = fixedPitch;

        this.serversideYaw = fixedYaw;
        this.serversidePitch = fixedPitch;
    };

    @EventHandler
    private final Listener<EventLook> eventLookListener = event -> {
        event.setYaw(getServersideYaw());
        event.setPitch(getServersidePitch());
    };

    @EventHandler
    private final Listener<EventJump> eventJumpListener = event -> event.setYaw(getServersideYaw());
}
