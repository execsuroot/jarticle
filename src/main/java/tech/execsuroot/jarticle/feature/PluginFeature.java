package tech.execsuroot.jarticle.feature;

import tech.execsuroot.jarticle.JarticlePlugin;

/**
 * Represents a feature of the plugin.
 */
public interface PluginFeature {

    /**
     * Called when the plugin is loaded.
     */
    default void onLoad(JarticlePlugin plugin) {
        // Empty by default for convenience
    }

    /**
     * Called when the plugin is enabled.
     */
    default void onEnable(JarticlePlugin plugin) {
        // Empty by default for convenience
    }

    /**
     * Called when the plugin is disabled.
     */
    default void onDisable(JarticlePlugin plugin) {
        // Empty by default for convenience
    }

    /**
     * Returns the name of this feature.
     */
    default String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Returns the priority of this feature.
     * The default is {@link Priority#Normal}.
     */
    default Priority getPriority() {
        return Priority.Normal;
    }

    /**
     * Priority levels for plugin features.
     * From highest to lowest.
     */
    enum Priority {Core, High, Normal, Low}
}
