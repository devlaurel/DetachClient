package dev.laurel.module;

import dev.laurel.module.impl.SprintModule;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class ModuleManager {
    private final Map<Class<? extends Module>, Module> modules;

    public ModuleManager() {
        this.modules = new HashMap<>();
        this.addModule(SprintModule.class);
    }

    @SneakyThrows
    private void addModule(Class<? extends Module> moduleClass) {
        this.modules.put(moduleClass, moduleClass.getConstructor().newInstance());
    }

    public Module getModule(Class<? extends Module> moduleClass) {
        return this.modules.get(moduleClass);
    }
}
