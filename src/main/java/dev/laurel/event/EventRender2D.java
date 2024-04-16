package dev.laurel.event;

import dev.codeman.eventbus.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@AllArgsConstructor
public final class EventRender2D extends Event {
    private final ScaledResolution scaledResolution;
    private final float partialTicks;
}
