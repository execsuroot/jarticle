package tech.execsuroot.jarticle.command;

import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import org.bukkit.command.CommandSender;
import tech.execsuroot.jarticle.JarticlePlugin;
import tech.execsuroot.jarticle.config.ConfigFeature;
import tech.execsuroot.jarticle.config.MessagesConfig;
import tech.execsuroot.jarticle.feature.FeatureManager;

/**
 * This class represents the main command of the plugin.
 */
@Command("jarticle")
@Permission("jarticle")
public class MainCommand {

    @Default
    public static void execute(CommandSender sender) {
        sender.sendMessage(MessagesConfig.getInstance().getMainCommandHelp());
    }

    @Subcommand("reload")
    @Permission("jarticle.reload")
    public static void reload(CommandSender sender) {
        JarticlePlugin plugin = JarticlePlugin.getInstance();
        FeatureManager featureManager = plugin.getFeatureManager();
        ConfigFeature configFeature = featureManager.getFeature(ConfigFeature.class).orElseThrow();
        Long startTimestamp = System.currentTimeMillis();
        configFeature.loadConfig();
        Long endTimestamp = System.currentTimeMillis();
        Long duration = endTimestamp - startTimestamp;
        sender.sendMessage(MessagesConfig.getInstance().getConfigReloaded(duration));
    }
}
