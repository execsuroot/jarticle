package tech.execsuroot.jarticle.elytra;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import tech.execsuroot.jarticle.JarticlePlugin;
import tech.execsuroot.jarticle.feature.PluginFeature;

public class ElytraFeature implements PluginFeature, Listener {

    private final ElytraManager elytraManager = new ElytraManager();

    @Override
    public void onEnable(JarticlePlugin plugin) {
        elytraManager.refreshCache();
        plugin.getServer().getPluginManager().registerEvents(elytraManager, plugin);
    }

    @Override
    public void onDisable(JarticlePlugin plugin) {
        HandlerList.unregisterAll(elytraManager);
    }
}
