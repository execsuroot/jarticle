package tech.execsuroot.jarticle.config;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ConfigReloadEvent extends Event {

    public static final ConfigReloadEvent INSTANCE = new ConfigReloadEvent();
    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private ConfigReloadEvent() {
        super(true);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
