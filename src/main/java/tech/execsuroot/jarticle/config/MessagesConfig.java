package tech.execsuroot.jarticle.config;

import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;

/**
 * This class represents the messages configuration file.
 * It is used to store messages and similar text components.
 */
@Getter
@Setter
@Configuration
public class MessagesConfig {

    /**
     * The instance of the messages configuration file.
     * Use to access the configuration.
     */
    @Getter
    @Setter
    private static MessagesConfig instance;

    private Component mainCommandHelp = MiniMessage.miniMessage().deserialize(
            """
                        <yellow>Usage:
                        <gray>/jarticle reload <white>— reload configuration.
                    """.stripIndent().stripTrailing()
    );
    private Component configReloaded = MiniMessage.miniMessage().deserialize(
            "<green>Configuration reloaded in <white>{duration}</white> ms."
    );
    private Component elytraWithIdNotFound = MiniMessage.miniMessage().deserialize(
            "<red>Elytra with ID <white>{id}</white> not found."
    );
    private Component elytraAddedToYourInventory = MiniMessage.miniMessage().deserialize(
            "<green>Elytra <white>{id}</white> added to your inventory."
    );

    public Component getElytraAddedToYourInventory(String id) {
        Component original = this.elytraAddedToYourInventory;
        TextReplacementConfig replacement = TextReplacementConfig.builder()
                .matchLiteral("{id}")
                .replacement(id)
                .build();
        return original.replaceText(replacement);
    }

    public Component getElytraWithIdNotFound(String id) {
        Component original = this.elytraWithIdNotFound;
        TextReplacementConfig replacement = TextReplacementConfig.builder()
                .matchLiteral("{id}")
                .replacement(id)
                .build();
        return original.replaceText(replacement);
    }

    public Component getConfigReloaded(Long duration) {
        Component original = this.configReloaded;
        TextReplacementConfig replacement = TextReplacementConfig.builder()
                .matchLiteral("{duration}")
                .replacement(String.valueOf(duration))
                .build();
        return original.replaceText(replacement);
    }
}
