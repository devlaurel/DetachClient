package dev.laurel.module;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModuleCategory {
    COMBAT("Combat"),
    MOTION("Motion"),
    PLAYER("Player"),
    VISUAL("Visual"),
    MISC("Misc"),;

    private final String name;
}
