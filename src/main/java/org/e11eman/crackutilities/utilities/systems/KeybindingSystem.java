package org.e11eman.crackutilities.utilities.systems;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.e11eman.crackutilities.keybinds.RandomRightClickKeybind;
import org.e11eman.crackutilities.keybinds.SpinKeybind;
import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.utilities.toolclasses.Keybind;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

public class KeybindingSystem {
    public HashMap<Keybind, KeyBinding> mappedKeybindings = new HashMap<>();

    public Keybind[] keybindings = {
            new RandomRightClickKeybind(),
            new SpinKeybind()
    };

    public KeybindingSystem() {
        for(Keybind keybind : keybindings) {
            mappedKeybindings.put(keybind, KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    keybind.name,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_UNKNOWN,
                    "Crackutilities"
            )));

        }

        CClient.events.register("tick", "keybindingsTick", (Event) -> mappedKeybindings.forEach((keybinding, binding) -> {
            while(binding.wasPressed()) {
                keybinding.execute();
            }
        }));
    }
}
