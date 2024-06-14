package tech.execsuroot.jarticle.hook;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import tech.execsuroot.jarticle.particle.AnimationPlayer;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseHook<T> implements Hook {

    private final Map<T, AnimationPlayer> ongoingAnimations = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        // Empty by default for hooks that don't need to do anything on enable
    }

    protected void startAnimation(T key, AnimationPlayer animation) {
        ongoingAnimations.put(key, animation);
    }

    protected void stopAnimation(T key) {
        ongoingAnimations.remove(key);
    }

    /**
     * Should override in order for the listener to be registered
     */
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
}
