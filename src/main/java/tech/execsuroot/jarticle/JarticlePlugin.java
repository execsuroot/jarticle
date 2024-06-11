package tech.execsuroot.jarticle;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import tech.execsuroot.jarticle.command.CommandFeature;
import tech.execsuroot.jarticle.config.ConfigFeature;
import tech.execsuroot.jarticle.elytra.ElytraFeature;
import tech.execsuroot.jarticle.feature.FeatureManager;
import tech.execsuroot.jarticle.feature.FeatureManagerImpl;
import tech.execsuroot.jarticle.feature.PluginFeature;
import tech.execsuroot.jarticle.util.Log;

/**
 * This class represents the main class of the plugin.
 */
public class JarticlePlugin extends JavaPlugin {

    /**
     * The instance of the plugin.
     * Use to access the plugin.
     */
    @Getter
    private static JarticlePlugin instance;
    /**
     * The feature manager of the plugin.
     * Use to get access to the features.
     */
    @Getter
    private final FeatureManager featureManager;

    public JarticlePlugin() {
        super();
        instance = this;
        Log.setLogger(getLogger());
        this.featureManager = new FeatureManagerImpl(this, getFeatures());
    }

    private PluginFeature[] getFeatures() {
        return new PluginFeature[]{
                new CommandFeature(),
                new ConfigFeature(),
                new ElytraFeature()
        };
    }

    @Override
    public void onLoad() {
        boolean loadedFeatures = featureManager.loadFeatures();
        if (loadedFeatures) {
            Log.info("✓ Loaded.");
        } else {
            Log.info("✗ Loaded with errors, the plugin may not work properly.");
        }
    }

    @Override
    public void onEnable() {
        boolean enabledFeatures = featureManager.enableFeatures();
        if (enabledFeatures) {
            Log.info("✓ Enabled.");
        } else {
            Log.info("✗ Enabled with errors, the plugin may not work properly, disabling...");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        boolean disabledFeatures = featureManager.disableFeatures();
        if (disabledFeatures) {
            Log.info("✓ Disabled.");
        } else {
            Log.info("✗ Disabled with errors, the plugin may not have been fully disabled.");
        }
    }
}
