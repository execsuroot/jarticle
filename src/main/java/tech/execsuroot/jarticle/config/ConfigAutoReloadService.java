package tech.execsuroot.jarticle.config;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import tech.execsuroot.jarticle.JarticlePlugin;
import tech.execsuroot.jarticle.util.Log;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;

@RequiredArgsConstructor
public class ConfigAutoReloadService {

    private final ConfigFeature configFeature;
    private BukkitTask task;
    private WatchService watchService;

    public boolean start() {
        try {
            watchService = FileSystems.getDefault().newWatchService();
            configFeature.getConfigFolder().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            Log.warn("Could not create watch service.", e);
            return false;
        }
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(JarticlePlugin.getInstance(), this::checkForChanges, 0, 20);
        return true;
    }

    private void checkForChanges() {
        var key = watchService.poll();
        if (key == null || key.pollEvents().isEmpty()) return;
        try {
            configFeature.loadConfig();
        } catch (Exception e) {
            Log.warn("Configuration auto-reload ignored some changes due to a corrupt configuration file.", e);
        } finally {
            key.pollEvents(); // Pooling events again to avoid a loop of events, as the loadConfig method might modify the file as well.
            key.reset();
        }
    }

    public void stop() {
        task.cancel();
        try {
            watchService.close();
        } catch (IOException e) {
            Log.warn("Could not close watch service.", e);
        }
    }

    public boolean isRunning() {
        return task != null && !task.isCancelled();
    }
}
