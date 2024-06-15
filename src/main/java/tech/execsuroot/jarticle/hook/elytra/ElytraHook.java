package tech.execsuroot.jarticle.hook.elytra;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import tech.execsuroot.jarticle.config.ConfigReloadEvent;
import tech.execsuroot.jarticle.config.MainConfig;
import tech.execsuroot.jarticle.hook.BaseHook;
import tech.execsuroot.jarticle.particle.AnimationData;
import tech.execsuroot.jarticle.particle.AnimationPlayer;
import tech.execsuroot.jarticle.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hook that starts animation for players gliding on elytra.
 */
public class ElytraHook extends BaseHook<Player> {

    private final Map<Integer, ElytraData> dataByCustomModel = new ConcurrentHashMap<>();

    @Override
    public void register(Plugin plugin) {
        cacheElytraData();
        super.register(plugin);
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
        AnimationPlayer animation = new AnimationPlayer(animationData);
        startAnimation(player, animation);
    }

    private ElytraData getElytraData(@NonNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        return meta.hasCustomModelData() ? dataByCustomModel.get(meta.getCustomModelData()) : null;
    }

    private void onPlayerStopGlide(Player player) {
        stopAnimation(player);
    }

    @EventHandler
    public void onConfigReload(ConfigReloadEvent event) {
        cacheElytraData();
    }

    private void cacheElytraData() {
        dataByCustomModel.clear();
        MainConfig.getInstance().getElytras().forEach((key, data) -> {
            dataByCustomModel.put(data.getCustomModelData(), data);
        });
    }

    @Override
    protected boolean shouldContinueAnimationFor(Player key) {
        return key.isOnline() && key.isGliding();
    }

    @Override
    protected Location getAnimationLocation(Player key) {
        return key.getLocation();
    }
}
