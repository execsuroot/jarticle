package tech.execsuroot.jarticle.hook;

import org.bukkit.plugin.Plugin;

public interface Hook {

    void register(Plugin plugin);

    void unregister();
}
