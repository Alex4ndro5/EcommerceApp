package com.infy.EcommerceApp.logModule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log2File {
    private static final Logger logger = LogManager.getLogger("Log");

    public Log2File() {
    }

    public static void log(Level level, Class clazz, String msg) {
        String message = String.format("[%s] : %s", clazz, msg);
        switch (level) {
            case Info:
                logger.info(message);
                break;
            case Warn:
                logger.warn(message);
                break;
            case Error:
                logger.error(message);
                break;
            case Fatal:
                logger.fatal(message);
                break;
            default:
                logger.debug(message);
        }

    }

    public static void log(Level level, Class clazz, String msg, Throwable throwable) {
        String message = String.format("[%s] : %s", clazz, msg);
        switch (level) {
            case Info:
                logger.info(message, throwable);
                break;
            case Warn:
                logger.warn(message, throwable);
                break;
            case Error:
                logger.error(message, throwable);
                break;
            case Fatal:
                logger.fatal(message, throwable);
                break;
            default:
                logger.debug(message, throwable);
        }

    }

    public static enum Level {
        Error,
        Warn,
        Fatal,
        Info;

        private Level() {
        }
    }
}
