package tech.execsuroot.jarticle.hook.permission;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import tech.execsuroot.jarticle.config.MainConfig;
import tech.execsuroot.jarticle.hook.BaseHook;
import tech.execsuroot.jarticle.particle.AnimationPlayer;

import java.util.Map;

public class PermissionHook extends BaseHook<Player> {

    // Should override in order for the listener to be registered
    @Override
    public void tickOngoingAnimations(ServerTickEndEvent event) {
        super.tickOngoingAnimations(event);
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
