package tech.execsuroot.jarticle.hook.bow;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
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
 * Hook that starts animation for arrows shot from bows.
 */
public class BowHook extends BaseHook<Entity> {

    private final Map<Integer, BowData> dataByCustomModel = new ConcurrentHashMap<>();

    @Override
    public void register(Plugin plugin) {
        cacheBowData();
        super.register(plugin);
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        Entity projectile = event.getProjectile();
        ItemStack bow = event.getBow();
        if (bow == null) return;
        BowData data = getBowData(bow);
        if (data == null) return;
        AnimationData animationData = MainConfig.getInstance().getAnimations().get(data.getAnimation());
        if (animationData == null) {
            Log.warn("Animation not found: " + data.getAnimation());
            return;
        }
        startAnimation(projectile, new AnimationPlayer(animationData));
    }

    private BowData getBowData(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        return meta.hasCustomModelData() ? dataByCustomModel.get(meta.getCustomModelData()) : null;
    }

    @EventHandler
    public void onConfigReload(ConfigReloadEvent event) {
        cacheBowData();
    }

    private void cacheBowData() {
        dataByCustomModel.clear();
        MainConfig.getInstance().getBows().forEach((key, data) -> {
            dataByCustomModel.put(data.getCustomModelData(), data);
        });
    }

    @Override
    protected boolean shouldContinueAnimationFor(Entity key) {
        return key.isValid() && !key.isOnGround() && !key.getVelocity().isZero();
    }

    @Override
    protected Location getAnimationLocation(Entity key) {
        return key.getLocation();
    }
}
