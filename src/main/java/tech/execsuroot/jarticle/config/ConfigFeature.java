package tech.execsuroot.jarticle.config;

import de.exlll.configlib.NameFormatters;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import tech.execsuroot.jarticle.JarticlePlugin;
import tech.execsuroot.jarticle.config.adventure.MiniMessageComponentSerializer;
import tech.execsuroot.jarticle.feature.PluginFeature;
import tech.execsuroot.jarticle.util.Log;

import java.nio.file.Path;

/**
 * This feature integrates the configuration files into the plugin.
 */
public class ConfigFeature implements PluginFeature {

    @Getter
    private final Path configFolder = JarticlePlugin.getInstance().getDataFolder().toPath();
    private final YamlConfigurationProperties yamlProperties = YamlConfigurationProperties.newBuilder()
            .addSerializer(Component.class, new MiniMessageComponentSerializer(MiniMessage.miniMessage()))
            .setNameFormatter(NameFormatters.IDENTITY)
            .build();
    private final ConfigAutoReloadService autoReloadService = new ConfigAutoReloadService(this);

    public boolean switchAutoReload() {
        if (autoReloadService.isRunning()) {
            autoReloadService.stop();
            Log.info("✗ Configuration auto-reload stopped.");
        } else {
            boolean hasStarted = autoReloadService.start();
            if (hasStarted) {
                Log.info("✓ Configuration auto-reload started.");
            } else {
                Log.warn("✗ Configuration auto-reload could not be started.");
            }
        }
        return autoReloadService.isRunning();
    }

    public void loadConfig() {
        MainConfig.setInstance(
                loadConfig(MainConfig.class, "config.yml")
        );
        MessagesConfig.setInstance(
                loadConfig(MessagesConfig.class, "messages.yml")
        );
        JarticlePlugin plugin = JarticlePlugin.getInstance();
        if (plugin.isEnabled()) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, ConfigReloadEvent.INSTANCE::callEvent);
        }
        Log.info("✓ Config reloaded.");
    }

    private <T> T loadConfig(Class<T> configClass, String fileName) {
        return YamlConfigurations.update(configFolder.resolve(fileName), configClass, yamlProperties);
    }

    @Override
    public void onLoad(JarticlePlugin plugin) {
        loadConfig();
    }

    @Override
    public Priority getPriority() {
        return Priority.Core;
    }
}
