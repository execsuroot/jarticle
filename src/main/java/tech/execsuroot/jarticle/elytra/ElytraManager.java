package tech.execsuroot.jarticle.elytra;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tech.execsuroot.jarticle.config.ConfigReloadEvent;
import tech.execsuroot.jarticle.config.MainConfig;
import tech.execsuroot.jarticle.particle.AnimationData;
import tech.execsuroot.jarticle.particle.AnimationPlayer;
import tech.execsuroot.jarticle.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ElytraManager implements Listener {

    private final Map<Integer, ElytraData> customModelDataToElytraData = new ConcurrentHashMap<>();
    private final Map<Player, AnimationPlayer> ongoingAnimations = new ConcurrentHashMap<>();

    @EventHandler
    public void tickOngoingAnimations(ServerTickEndEvent event) {
        for (Map.Entry<Player, AnimationPlayer> entry : ongoingAnimations.entrySet()) {
            Player player = entry.getKey();
            AnimationPlayer animationPlayer = entry.getValue();
            animationPlayer.playTick(player.getLocation());
        }
    }

    @EventHandler
    public void onToggleGlide(EntityToggleGlideEvent event) {
        Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;
        if (player == null) return;
        if (event.isGliding()) {
            onPlayerStartGlide(player);
        } else {
            onPlayerStopGlide(player);
        }
    }

    private void onPlayerStartGlide(Player player) {
        ItemStack chestplate = player.getInventory().getChestplate();
        if (chestplate == null || chestplate.getType() != Material.ELYTRA) return;
        ElytraData elytraData = getElytraData(chestplate);
        if (elytraData == null) return;
        AnimationData animationData = MainConfig.getInstance().getAnimations().get(elytraData.getAnimation());
        if (animationData == null) {
            Log.warn("Animation not found: " + elytraData.getAnimation());
            return;
        }
        AnimationPlayer playerAnimation = new AnimationPlayer(animationData);
        ongoingAnimations.put(player, playerAnimation);
    }

    private void onPlayerStopGlide(Player player) {
        // ongoingAnimations.remove(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        ongoingAnimations.remove(event.getPlayer());
    }

    @EventHandler
    public void onConfigReload(ConfigReloadEvent event) {
        refreshCache();
    }

    public void refreshCache() {
        customModelDataToElytraData.clear();
        for (ElytraData elytraData : MainConfig.getInstance().getElytras().values()) {
            customModelDataToElytraData.put(elytraData.getCustomModelData(), elytraData);
        }
    }

    private ElytraData getElytraData(@NonNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        return meta.hasCustomModelData() ? customModelDataToElytraData.get(meta.getCustomModelData()) : null;
    }
}
