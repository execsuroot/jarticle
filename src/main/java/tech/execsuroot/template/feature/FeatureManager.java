package tech.execsuroot.template.feature;

import java.util.Optional;

/**
 * This interface represents a feature manager.
 * It is responsible for loading, enabling and disabling features.
 */
public interface FeatureManager {

    /**
     * Loads the features.
     *
     * @return true if the features were loaded successfully, false otherwise
     */
    boolean loadFeatures();

    /**
     * Enables the features.
     *
     * @return true if the features were enabled successfully, false otherwise
     */
    boolean enableFeatures();

    /**
     * Disables the features.
     *
     * @return true if the features were disabled successfully, false otherwise
     */
    boolean disableFeatures();

    /**
     * Gets a feature by its class.
     *
     * @param featureClass the feature class
     * @param <T>          the feature type
     * @return the feature or null if it does not exist
     */
    <T extends PluginFeature> Optional<T> getFeature(Class<T> featureClass);
}
