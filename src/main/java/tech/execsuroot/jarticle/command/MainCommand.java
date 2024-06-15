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
import tech.execsuroot.jarticle.hook.bow.BowData;
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
            })
            .withSubcommands(
                    new CommandAPICommand("auto")
                            .withPermission("jarticle.reload.auto")
                            .executes((sender, args) -> {
                                JarticlePlugin plugin = JarticlePlugin.getInstance();
                                FeatureManager featureManager = plugin.getFeatureManager();
                                ConfigFeature configFeature = featureManager.getFeature(ConfigFeature.class).orElseThrow();
                                boolean isAutoReloadEnabled = configFeature.switchAutoReload();
                                if (isAutoReloadEnabled) {
                                    sender.sendMessage(MessagesConfig.getInstance().getConfigAutoReloadEnabled());
                                } else {
                                    sender.sendMessage(MessagesConfig.getInstance().getConfigAutoReloadDisabled());
                                }
                            })
            );

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

    private static final CommandAPICommand bow = new CommandAPICommand("bow")
            .withPermission("jarticle.bow")
            .withArguments(List.of(
                    new StringArgument("id")
                            .replaceSuggestions(ArgumentSuggestions.stringCollection((sender) -> MainConfig.getInstance().getBows().keySet()))
            ))
            .executesPlayer((sender, args) -> {
                String id = args.getByClass(0, String.class);
                BowData bowData = MainConfig.getInstance().getBows().get(id);
                if (bowData == null) {
                    sender.sendMessage(MessagesConfig.getInstance().getBowWithIdNotFound(id));
                    return;
                }
                ItemStack bow = new ItemStack(Material.BOW);
                ItemMeta bowMeta = bow.getItemMeta();
                bowMeta.displayName(bowData.getName());
                bowMeta.lore(List.of(bowData.getLore()));
                bowMeta.setCustomModelData(bowData.getCustomModelData());
                bow.setItemMeta(bowMeta);
                sender.getInventory().addItem(bow);
                sender.sendMessage(MessagesConfig.getInstance().getBowAddedToYourInventory(id));
            });

    private static final CommandAPICommand root = new CommandAPICommand("jarticle")
            .withPermission("jarticle")
            .executes((sender, args) -> {
                sender.sendMessage(MessagesConfig.getInstance().getMainCommandHelp());
            })
            .withSubcommands(reload, elytra, bow);

    public static void register(JarticlePlugin plugin) {
        root.register(plugin);
    }
}
