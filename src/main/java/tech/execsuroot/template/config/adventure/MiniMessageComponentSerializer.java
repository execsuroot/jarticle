package tech.execsuroot.template.config.adventure;

import de.exlll.configlib.Serializer;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

/**
 * A serializer for {@link Component} using {@link MiniMessage}.
 */
@RequiredArgsConstructor
public class MiniMessageComponentSerializer implements Serializer<Component, String> {

    private final MiniMessage miniMessage;

    @Override
    public String serialize(Component element) {
        return miniMessage.serialize(element);
    }

    @Override
    public Component deserialize(String element) {
        return miniMessage.deserialize(element);
    }
}
