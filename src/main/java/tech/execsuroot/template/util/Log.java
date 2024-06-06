package tech.execsuroot.template.util;

import lombok.Setter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Just a nice wrapper around the plugin's logger for convenience.
 */
public class Log {

    @Setter
    private static Logger logger;

    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    public static void warn(String message) {
        logger.log(Level.WARNING, message);
    }

    public static void warn(String message, Throwable throwable) {
        logger.log(Level.WARNING, message, throwable);
    }

    public static void severe(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }
}
