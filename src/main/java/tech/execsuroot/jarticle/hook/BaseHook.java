package tech.execsuroot.jarticle.hook;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import tech.execsuroot.jarticle.particle.AnimationPlayer;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseHook<T> implements Hook, Listener {

    protected final Map<T, AnimationPlayer> ongoingAnimations = new ConcurrentHashMap<>();

    protected void startAnimation(T key, AnimationPlayer animation) {
        ongoingAnimations.put(key, animation);
    }

    protected void stopAnimation(T key) {
        ongoingAnimations.remove(key);
    }

    @EventHandler
    public void tickOngoingAnimations(ServerTickEndEvent event) {
        Iterator<Map.Entry<T, AnimationPlayer>> iterator = ongoingAnimations.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<T, AnimationPlayer> entry = iterator.next();
            T key = entry.getKey();
            AnimationPlayer animation = entry.getValue();
            boolean shouldContinue = shouldContinueAnimationFor(key) && animation.playTick(getAnimationLocation(key));
            if (!shouldContinue) iterator.remove();
        }
    }

    protected abstract boolean shouldContinueAnimationFor(T key);

    protected abstract Location getAnimationLocation(T key);

    @Override
    public void register(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void unregister() {
        HandlerList.unregisterAll(this);
    }
}
