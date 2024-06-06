package tech.execsuroot.template.config;

import de.exlll.configlib.NameFormatters;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import tech.execsuroot.template.TemplatePlugin;
import tech.execsuroot.template.config.adventure.MiniMessageComponentSerializer;
import tech.execsuroot.template.feature.PluginFeature;
import tech.execsuroot.template.util.Log;

import java.nio.file.Path;

/**
 * This feature integrates the configuration files into the plugin.
 */
public class ConfigFeature implements PluginFeature {

    private final Path configFolder = TemplatePlugin.getInstance().getDataFolder().toPath();
    private final YamlConfigurationProperties yamlProperties = YamlConfigurationProperties.newBuilder()
            .addSerializer(Component.class, new MiniMessageComponentSerializer(MiniMessage.miniMessage()))
            .setNameFormatter(NameFormatters.IDENTITY)
            .build();

    public void loadConfig() {
        MainConfig.setInstance(
                loadConfig(MainConfig.class, "config.yml")
        );
        MessagesConfig.setInstance(
                loadConfig(MessagesConfig.class, "messages.yml")
        );
        // ToDo: Include your config here, examples are above
        Log.info("✓ Config reloaded.");
    }

    private <T> T loadConfig(Class<T> configClass, String fileName) {
        return YamlConfigurations.update(configFolder.resolve(fileName), configClass, yamlProperties);
    }

    @Override
    public void onLoad(TemplatePlugin plugin) {
        loadConfig();
    }

    @Override
    public Priority getPriority() {
        return Priority.Core;
    }
}
