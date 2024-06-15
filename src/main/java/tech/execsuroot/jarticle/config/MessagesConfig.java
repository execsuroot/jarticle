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
    private Component bowWithIdNotFound = MiniMessage.miniMessage().deserialize(
            "<red>Bow with ID <white>{id}</white> not found."
    );
    private Component bowAddedToYourInventory = MiniMessage.miniMessage().deserialize(
            "<green>Bow <white>{id}</white> added to your inventory."
    );
    private Component configAutoReloadEnabled = MiniMessage.miniMessage().deserialize(
            "<green>Configuration auto-reload enabled."
    );
    private Component configAutoReloadDisabled = MiniMessage.miniMessage().deserialize(
            "<red>Configuration auto-reload disabled."
    );

    public Component getBowAddedToYourInventory(String id) {
        Component original = this.bowAddedToYourInventory;
        TextReplacementConfig replacement = TextReplacementConfig.builder()
                .matchLiteral("{id}")
                .replacement(id)
                .build();
        return original.replaceText(replacement);
    }

    public Component getBowWithIdNotFound(String id) {
        Component original = this.bowWithIdNotFound;
        TextReplacementConfig replacement = TextReplacementConfig.builder()
                .matchLiteral("{id}")
                .replacement(id)
                .build();
        return original.replaceText(replacement);
    }

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
