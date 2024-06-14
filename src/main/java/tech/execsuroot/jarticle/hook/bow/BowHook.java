package tech.execsuroot.jarticle.hook.bow;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tech.execsuroot.jarticle.config.ConfigReloadEvent;
import tech.execsuroot.jarticle.config.MainConfig;
import tech.execsuroot.jarticle.hook.BaseHook;
import tech.execsuroot.jarticle.particle.AnimationData;
import tech.execsuroot.jarticle.particle.AnimationPlayer;
import tech.execsuroot.jarticle.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BowHook extends BaseHook<Entity> {

    private final Map<Integer, BowData> bowDataByCustomModel = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        cacheBowData();
    }

    // Should override in order for the listener to be registered
    @Override
    public void tickOngoingAnimations(ServerTickEndEvent event) {
        super.tickOngoingAnimations(event);
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        Entity projectile = event.getProjectile();
        ItemStack bow = event.getBow();
        if (bow == null) return;
        ItemMeta meta = bow.getItemMeta();
        if (!meta.hasCustomModelData()) return;
        BowData data = bowDataByCustomModel.get(meta.getCustomModelData());
        if (data == null) return;
        AnimationData animationData = MainConfig.getInstance().getAnimations().get(data.getAnimation());
        if (animationData == null) {
            Log.warn("Animation not found: " + data.getAnimation());
            return;
        }
        startAnimation(projectile, new AnimationPlayer(animationData));
    }

    @EventHandler
    public void onConfigReload(ConfigReloadEvent event) {
        cacheBowData();
    }

    private void cacheBowData() {
        bowDataByCustomModel.clear();
        MainConfig.getInstance().getBows().forEach((key, data) -> {
            bowDataByCustomModel.put(data.getCustomModelData(), data);
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
