package tech.execsuroot.jarticle.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tech.execsuroot.jarticle.JarticlePlugin;
import tech.execsuroot.jarticle.config.ConfigFeature;
import tech.execsuroot.jarticle.config.MainConfig;
import tech.execsuroot.jarticle.config.MessagesConfig;
import tech.execsuroot.jarticle.hook.elytra.ElytraData;
import tech.execsuroot.jarticle.feature.FeatureManager;

import java.util.List;

/**
 * This class represents the main command of the plugin.
 */
@Command("jarticle")
@Permission("jarticle")
public class MainCommand {

    private static final CommandAPICommand reload = new CommandAPICommand("reload")
            .withPermission("jarticle.reload")
            .executes((sender, args) -> {
                JarticlePlugin plugin = JarticlePlugin.getInstance();
                FeatureManager featureManager = plugin.getFeatureManager();
                ConfigFeature configFeature = featureManager.getFeature(ConfigFeature.class).orElseThrow();
                Long startTimestamp = System.currentTimeMillis();
                configFeature.loadConfig();
                Long endTimestamp = System.currentTimeMillis();
                Long duration = endTimestamp - startTimestamp;
                sender.sendMessage(MessagesConfig.getInstance().getConfigReloaded(duration));
            });

    private static final CommandAPICommand elytra = new CommandAPICommand("elytra")
            .withPermission("jarticle.elytra")
            .withArguments(List.of(
                    new StringArgument("id")
                            .replaceSuggestions(ArgumentSuggestions.stringCollection((sender) -> MainConfig.getInstance().getElytras().keySet()))
            ))
            .executesPlayer((sender, args) -> {
                String id = args.getByClass(0, String.class);
                ElytraData elytraData = MainConfig.getInstance().getElytras().get(id);
                if (elytraData == null) {
                    sender.sendMessage(MessagesConfig.getInstance().getElytraWithIdNotFound(id));
                    return;
                }
                ItemStack elytra = new ItemStack(Material.ELYTRA);
                ItemMeta elytraMeta = elytra.getItemMeta();
                elytraMeta.displayName(elytraData.getName());
                elytraMeta.lore(List.of(elytraData.getLore()));
                elytraMeta.setCustomModelData(elytraData.getCustomModelData());
                elytra.setItemMeta(elytraMeta);
                sender.getInventory().addItem(elytra);
                sender.sendMessage(MessagesConfig.getInstance().getElytraAddedToYourInventory(id));
            });

    private static final CommandAPICommand root = new CommandAPICommand("jarticle")
            .withPermission("jarticle")
            .executes((sender, args) -> {
                sender.sendMessage(MessagesConfig.getInstance().getMainCommandHelp());
            })
            .withSubcommands(reload, elytra);

    public static void register(JarticlePlugin plugin) {
        root.register(plugin);
    }
}
