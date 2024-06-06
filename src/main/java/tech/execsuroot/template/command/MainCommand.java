package tech.execsuroot.template.command;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import org.bukkit.command.CommandSender;
import tech.execsuroot.template.TemplatePlugin;
import tech.execsuroot.template.config.ConfigFeature;
import tech.execsuroot.template.config.MessagesConfig;
import tech.execsuroot.template.feature.FeatureManager;

/**
 * This class represents the main command of the plugin.
 */
@Command("template") // ToDo: Change the command name
@Permission("template.cmd") // ToDo: Change the command permission
public class MainCommand {

    @Default
    public static void execute(CommandSender sender) {
        sender.sendMessage(MessagesConfig.getInstance().getMainCommandHelp());
    }

    @Subcommand("reload")
    public static void reload(CommandSender sender) {
        TemplatePlugin plugin = TemplatePlugin.getInstance();
        FeatureManager featureManager = plugin.getFeatureManager();
        ConfigFeature configFeature = featureManager.getFeature(ConfigFeature.class).orElseThrow();
        Long startTimestamp = System.currentTimeMillis();
        configFeature.loadConfig();
        Long endTimestamp = System.currentTimeMillis();
        Long duration = endTimestamp - startTimestamp;
        sender.sendMessage(MessagesConfig.getInstance().getConfigReloaded(duration));
    }
}
