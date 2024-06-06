package tech.execsuroot.template; // ToDo: Change to your package name

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import tech.execsuroot.template.command.CommandFeature;
import tech.execsuroot.template.config.ConfigFeature;
import tech.execsuroot.template.feature.FeatureManager;
import tech.execsuroot.template.feature.FeatureManagerImpl;
import tech.execsuroot.template.feature.PluginFeature;
import tech.execsuroot.template.util.Log;

/**
 * This class represents the main class of the plugin.
 */
// ToDo: Change to your plugin name
public class TemplatePlugin extends JavaPlugin {

    /**
     * The instance of the plugin.
     * Use to access the plugin.
     */
    @Getter
    private static TemplatePlugin instance;
    /**
     * The feature manager of the plugin.
     * Use to get access to the features.
     */
    @Getter
    private final FeatureManager featureManager;

    public TemplatePlugin() {
        super();
        instance = this;
        Log.setLogger(getLogger());
        this.featureManager = new FeatureManagerImpl(this, getFeatures());
    }

    private PluginFeature[] getFeatures() {
        return new PluginFeature[]{
                new CommandFeature(),
                new ConfigFeature(),
                // ToDo: Include your features here
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
