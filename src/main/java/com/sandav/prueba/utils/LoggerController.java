package com.sandav.prueba.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerController {

    private static final Logger logger = LoggerFactory.getLogger(LoggerController.class);

    public void info(String message) {
        logger.info(message);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void debug(String message) {
        logger.debug(message);
    }
}
