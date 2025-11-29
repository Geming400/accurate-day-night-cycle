package geming400.accuratedaynightcycle;

import geming400.accuratedaynightcycle.devui.QuaternionSlidersUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public class AccurateDaynightCycleClient implements ClientModInitializer {
    private static final KeyBinding SHOW_MENU = new KeyBinding("key.adnc.dev-menu-show", GLFW.GLFW_KEY_H, "key.categories.misc");
    private static final KeyBinding RELOAD_MENU = new KeyBinding("key.adnc.reload-menu", GLFW.GLFW_KEY_R, "key.categories.misc");

    @Nullable
    public static QuaternionSlidersUI quaternions = null;

    @Override
    public void onInitializeClient() {
        quaternions = new QuaternionSlidersUI();

        KeyBindingHelper.registerKeyBinding(SHOW_MENU);
        KeyBindingHelper.registerKeyBinding(RELOAD_MENU);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (SHOW_MENU.wasPressed()) {
                client.setScreen(quaternions);
            }

            while (RELOAD_MENU.wasPressed()) {
                quaternions = new QuaternionSlidersUI();
            }
        });
    }
}