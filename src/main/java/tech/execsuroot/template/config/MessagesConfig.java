package tech.execsuroot.template.config;

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

    // ToDo: Change the messages below
    private Component mainCommandHelp = MiniMessage.miniMessage().deserialize(
            """
                        <yellow>Usage:
                        <gray>/template reload <white>— reload configuration.
                    """.stripIndent().stripTrailing()
    );
    private Component configReloaded = MiniMessage.miniMessage().deserialize(
            "<green>Configuration reloaded in <white>{duration}</white> ms."
    );
    // ToDo: Include your messages here

    public Component getConfigReloaded(Long duration) {
        Component original = this.configReloaded;
        TextReplacementConfig replacement = TextReplacementConfig.builder()
                .matchLiteral("{duration}")
                .replacement(String.valueOf(duration))
                .build();
        return original.replaceText(replacement);
    }
}
