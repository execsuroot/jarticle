package tech.execsuroot.template.command;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.jorel.commandapi.CommandAPILogger;
import tech.execsuroot.template.TemplatePlugin;
import tech.execsuroot.template.feature.PluginFeature;

import java.util.Set;

/**
 * This feature integrates the CommandAPI library into the plugin.
 */
public class CommandFeature implements PluginFeature {

    @Override
    public void onLoad(TemplatePlugin plugin) {
        CommandAPI.setLogger(CommandAPILogger.fromJavaLogger(plugin.getLogger()));
        CommandAPI.onLoad(new CommandAPIBukkitConfig(plugin).usePluginNamespace());
        Set.of(
                MainCommand.class
                // ToDo: Include your command classes here
        ).forEach(CommandAPI::registerCommand);
    }

    @Override
    public void onEnable(TemplatePlugin plugin) {
        CommandAPI.onEnable();
    }

    @Override
    public void onDisable(TemplatePlugin plugin) {
        CommandAPI.onDisable();
    }

    @Override
    public Priority getPriority() {
        return Priority.High;
    }
}
