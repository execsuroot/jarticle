package tech.execsuroot.jarticle.command;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandAPILogger;
import tech.execsuroot.jarticle.JarticlePlugin;
import tech.execsuroot.jarticle.feature.PluginFeature;

import java.util.Set;

/**
 * This feature integrates the CommandAPI library into the plugin.
 */
public class CommandFeature implements PluginFeature {

    @Override
    public void onLoad(JarticlePlugin plugin) {
        CommandAPI.setLogger(CommandAPILogger.fromJavaLogger(plugin.getLogger()));
        CommandAPI.onLoad(new CommandAPIBukkitConfig(plugin).usePluginNamespace());
        Set.of(
                MainCommand.class
        ).forEach(CommandAPI::registerCommand);
    }

    @Override
    public void onEnable(JarticlePlugin plugin) {
        CommandAPI.onEnable();
    }

    @Override
    public void onDisable(JarticlePlugin plugin) {
        CommandAPI.onDisable();
    }

    @Override
    public Priority getPriority() {
        return Priority.High;
    }
}
