package tech.execsuroot.template.feature;

import lombok.NonNull;
import tech.execsuroot.template.TemplatePlugin;
import tech.execsuroot.template.util.Log;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FeatureManagerImpl implements FeatureManager {

    private final TemplatePlugin plugin;
    private final List<PluginFeature> orderedFeatures;
    private final Map<Class<? extends PluginFeature>, PluginFeature> featureByClassMap;
    private final Set<PluginFeature> enabledFeatures = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public FeatureManagerImpl(@NonNull TemplatePlugin plugin, @NonNull PluginFeature[] features) {
        this.plugin = plugin;
        this.orderedFeatures = Arrays.stream(features).sorted(Comparator.comparing(PluginFeature::getPriority)).collect(Collectors.toList());
        this.featureByClassMap = toFeatureMap(features);
    }

    @Override
    public boolean loadFeatures() {
        long startTimestamp = System.currentTimeMillis();
        for (PluginFeature feature : orderedFeatures) {
            if (!loadFeature(feature)) return false;
        }
        long endTimestamp = System.currentTimeMillis();
        long duration = endTimestamp - startTimestamp;
        Log.info("Loaded " + orderedFeatures.size() + " features in " + duration + " ms.");
        return true;
    }

    private boolean loadFeature(PluginFeature feature) {
        try {
            long startTimestamp = System.currentTimeMillis();
            feature.onLoad(plugin);
            long endTimestamp = System.currentTimeMillis();
            long duration = endTimestamp - startTimestamp;
            Log.info("Loaded feature " + feature.getName() + " in " + duration + "ms.");
            return true;
        } catch (Exception e) {
            Log.severe("Failed to load feature " + feature.getName() + " due to an error.", e);
            return false;
        }
    }

    @Override
    public boolean enableFeatures() {
        long startTimestamp = System.currentTimeMillis();
        for (PluginFeature feature : orderedFeatures) {
            if (!enableFeature(feature)) return false;
        }
        long endTimestamp = System.currentTimeMillis();
        long duration = endTimestamp - startTimestamp;
        Log.info("Enabled " + orderedFeatures.size() + " features in " + duration + " ms.");
        return true;
    }

    private boolean enableFeature(PluginFeature feature) {
        try {
            long startTimestamp = System.currentTimeMillis();
            feature.onEnable(plugin);
            enabledFeatures.add(feature);
            long endTimestamp = System.currentTimeMillis();
            long duration = endTimestamp - startTimestamp;
            Log.info("Enabled feature " + feature.getName() + " in " + duration + "ms.");
            return true;
        } catch (Exception e) {
            Log.severe("Failed to enable feature " + feature.getName() + " due to an error.", e);
            return false;
        }
    }

    @Override
    public boolean disableFeatures() {
        List<PluginFeature> reversedOrderedFeatures = orderedFeatures.reversed();
        int disabledCount = 0;
        long startTimestamp = System.currentTimeMillis();
        for (PluginFeature feature : reversedOrderedFeatures) {
            if (!enabledFeatures.contains(feature)) continue;
            if (!disableFeature(feature)) return false;
            disabledCount++;
        }
        long endTimestamp = System.currentTimeMillis();
        long duration = endTimestamp - startTimestamp;
        Log.info("Disabled " + disabledCount + " features in " + duration + " ms.");
        return true;
    }

    private boolean disableFeature(PluginFeature feature) {
        try {
            long startTimestamp = System.currentTimeMillis();
            feature.onDisable(plugin);
            enabledFeatures.remove(feature);
            long endTimestamp = System.currentTimeMillis();
            long duration = endTimestamp - startTimestamp;
            Log.info("Disabled feature " + feature.getName() + " in " + duration + "ms.");
            return true;
        } catch (Exception e) {
            Log.severe("Failed to disable feature " + feature.getName() + " due to an error.", e);
            return false;
        }
    }

    private Map<Class<? extends PluginFeature>, PluginFeature> toFeatureMap(PluginFeature[] features) {
        return Arrays.stream(features).collect(Collectors.toMap(PluginFeature::getClass, Function.identity()));
    }

    @Override
    public <T extends PluginFeature> Optional<T> getFeature(@NonNull Class<T> featureClass) {
        PluginFeature feature = featureByClassMap.get(featureClass);
        if (feature == null) return Optional.empty();
        return Optional.of(featureClass.cast(feature));
    }
}
