package tech.execsuroot.jarticle.hook.permission;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import tech.execsuroot.jarticle.config.MainConfig;
import tech.execsuroot.jarticle.hook.BaseHook;
import tech.execsuroot.jarticle.particle.AnimationPlayer;

import java.util.Map;

public class PermissionHook extends BaseHook {

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
}
