package dev.laurel.module;

import dev.laurel.module.impl.combat.*;
import dev.laurel.module.impl.fun.SpinBotModule;
import dev.laurel.module.impl.misc.TimerModule;
import dev.laurel.module.impl.movement.*;
import dev.laurel.module.impl.player.*;
import dev.laurel.module.impl.visual.*;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public final class ModuleManager {
    private final Map<Class<? extends Module>, Module> modules;

    public ModuleManager() {
        this.modules = new HashMap<>();
        this.addModule(AimAssist.class);
        this.addModule(AnimationsModule.class);
        this.addModule(AutoClickerModule.class);
        this.addModule(AutoWalkModule.class);
        this.addModule(BrightnessModule.class);
        this.addModule(ChestStealerModule.class);
        this.addModule(ClickGUIModule.class);
        this.addModule(ESPModule.class);
        this.addModule(FastPlaceModule.class);
        this.addModule(FlightModule.class);
        this.addModule(HUDModule.class);
        this.addModule(InventoryMoveModule.class);
        this.addModule(KillAuraModule.class);
        this.addModule(MovementFixModule.class);
        this.addModule(NoClickDelayModule.class);
        this.addModule(NoFallModule.class);
        this.addModule(NoFireModule.class);
        this.addModule(NoHurtCamModule.class);
        this.addModule(NoJumpDelayModule.class);
        this.addModule(NoRotateSetModule.class);
        this.addModule(NoSlowDownModule.class);
        this.addModule(VelocityModule.class);
        this.addModule(ScoreboardModule.class);
        this.addModule(SpinBotModule.class);
        this.addModule(SprintModule.class);
        this.addModule(StepModule.class);
        this.addModule(TimerModule.class);
    }

    public List<Module> getModulesInCategory(ModuleCategory moduleCategory) {
        return this.modules.values().stream()
                .filter(module -> module.getModuleCategory() == moduleCategory)
                .sorted(Comparator.comparing(Module::getName))
                .collect(Collectors.toList());
    }


    @SneakyThrows
    private void addModule(Class<? extends Module> moduleClass) {
        this.modules.put(moduleClass, moduleClass.getConstructor().newInstance());
    }

    public Module getModule(Class<? extends Module> moduleClass) {
        return this.modules.get(moduleClass);
    }
}
