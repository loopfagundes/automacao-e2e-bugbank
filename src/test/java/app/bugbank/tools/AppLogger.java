package app.bugbank.tools;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppLogger {
    private static final Logger logger = Logger.getLogger(AppLogger.class.getName());

    public static void logInfo(String msg) {
        logger.log(Level.INFO, msg);
    }

    public static void logWarning(String msg) {
        logger.log(Level.WARNING, msg);
    }
}