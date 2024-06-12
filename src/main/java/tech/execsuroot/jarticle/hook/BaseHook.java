package tech.execsuroot.jarticle.hook;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import tech.execsuroot.jarticle.particle.AnimationPlayer;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseHook implements Hook {

    private final Map<Player, AnimationPlayer> ongoingAnimations = new ConcurrentHashMap<>();

    protected void startAnimation(Player player, AnimationPlayer animation) {
        ongoingAnimations.put(player, animation);
    }

    protected void stopAnimation(Player player) {
        ongoingAnimations.remove(player);
    }

    @EventHandler
    public void tickOngoingAnimations(ServerTickEndEvent event) {
        Iterator<Map.Entry<Player, AnimationPlayer>> iterator = ongoingAnimations.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Player, AnimationPlayer> entry = iterator.next();
            Player player = entry.getKey();
            AnimationPlayer animation = entry.getValue();
            if (player.isOnline() && shouldContinueAnimation(player)) {
                animation.playTick(player.getLocation());
            } else {
                iterator.remove();
            }
        }
    }

    protected boolean shouldContinueAnimation(Player player) {
        return true;
    }
}
