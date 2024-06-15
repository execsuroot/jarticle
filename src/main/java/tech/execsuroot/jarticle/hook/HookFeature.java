package tech.execsuroot.jarticle.hook;

import tech.execsuroot.jarticle.JarticlePlugin;
import tech.execsuroot.jarticle.feature.PluginFeature;
import tech.execsuroot.jarticle.hook.bow.BowHook;
import tech.execsuroot.jarticle.hook.elytra.ElytraHook;
import tech.execsuroot.jarticle.hook.permission.PermissionHook;

import java.util.List;

public class HookFeature implements PluginFeature {

    private final List<Hook> hooks = List.of(new ElytraHook(), new PermissionHook(), new BowHook());

    @Override
    public void onEnable(JarticlePlugin plugin) {
        for (Hook hook : hooks) {
            hook.register(plugin);
        }
    }

    @Override
    public void onDisable(JarticlePlugin plugin) {
        for (Hook hook : hooks) {
            hook.unregister();
        }
    }
}
