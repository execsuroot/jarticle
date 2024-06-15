package tech.execsuroot.jarticle.hook.permission;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import tech.execsuroot.jarticle.config.ConfigReloadEvent;
import tech.execsuroot.jarticle.config.MainConfig;
import tech.execsuroot.jarticle.hook.BaseHook;
import tech.execsuroot.jarticle.particle.AnimationPlayer;

import java.util.List;
import java.util.Map;

/**
 * Hook that starts animation for players with specific permission.
 */
public class PermissionHook extends BaseHook<Player> {

    @EventHandler
    public void onConfigReload(ConfigReloadEvent event) {
        List<Player> players = List.copyOf(ongoingAnimations.keySet());
        for (Player player : players) {
            startPermissionAnimationIfHas(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        startPermissionAnimationIfHas(player);
    }

    private void startPermissionAnimationIfHas(Player player) {
        MainConfig mainConfig = MainConfig.getInstance();
        Map<String, String> permissionHooks = mainConfig.getPermissions();
        permissionHooks.forEach((permission, animationId) -> {
            if (player.hasPermission(permission)) {
                AnimationPlayer playerAnimation = new AnimationPlayer(mainConfig.getAnimations().get(animationId));
                startAnimation(player, playerAnimation);
            }
        });
    }

    @Override
    protected boolean shouldContinueAnimationFor(Player key) {
        return key.isOnline();
    }

    @Override
    protected Location getAnimationLocation(Player key) {
        return key.getLocation();
    }
}
