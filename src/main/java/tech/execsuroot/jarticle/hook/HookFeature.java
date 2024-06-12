package tech.execsuroot.jarticle.hook;

import org.bukkit.event.HandlerList;
import tech.execsuroot.jarticle.JarticlePlugin;
import tech.execsuroot.jarticle.feature.PluginFeature;
import tech.execsuroot.jarticle.hook.elytra.ElytraHook;
import tech.execsuroot.jarticle.hook.permission.PermissionHook;

import java.util.List;

public class HookFeature implements PluginFeature {

    private final List<Hook> hooks = List.of(
            new ElytraHook(), new PermissionHook()
    );

    @Override
    public void onEnable(JarticlePlugin plugin) {
        hooks.forEach(hook -> plugin.getServer().getPluginManager().registerEvents(hook, plugin));
    }

    @Override
    public void onDisable(JarticlePlugin plugin) {
        hooks.forEach(HandlerList::unregisterAll);
    }
}
