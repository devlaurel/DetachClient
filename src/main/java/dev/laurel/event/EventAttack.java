package dev.laurel.event;

import dev.codeman.eventbus.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@Getter
@AllArgsConstructor
public class EventAttack extends Event {
    private final EntityPlayer playerIn;
    private final Entity targetEntity;
}
