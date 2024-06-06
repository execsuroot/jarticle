package tech.execsuroot.template.feature;

import tech.execsuroot.template.TemplatePlugin;

/**
 * Represents a feature of the plugin.
 */
public interface PluginFeature {

    /**
     * Called when the plugin is loaded.
     */
    default void onLoad(TemplatePlugin plugin) {
        // Empty by default for convenience
    }

    /**
     * Called when the plugin is enabled.
     */
    default void onEnable(TemplatePlugin plugin) {
        // Empty by default for convenience
    }

    /**
     * Called when the plugin is disabled.
     */
    default void onDisable(TemplatePlugin plugin) {
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
