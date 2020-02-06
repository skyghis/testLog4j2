package com.test;

import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;

public final class App {

    static {
//        System.setProperty("log4j2.debug", "true");
    }
    private static final Logger LOGGER = LogManager.getLogger(App.class);
    private static final Path log4jConfiguration = Path.of("log4j2-outer.properties").toAbsolutePath();

    public static void main(String[] args) throws Exception {
        if (!Files.isReadable(log4jConfiguration)) {
            throw new IllegalStateException();
        }

        LOGGER.info("startup");
        LOGGER.debug("startup"); // Not logged = OK

        Configurator.initialize(null, log4jConfiguration.toString());
        LOGGER.debug("with init"); // Should be logged

        // From log4j FAQ : https://logging.apache.org/log4j/2.x/faq.html#reconfig_from_code
        final LoggerContext context = (LoggerContext) LogManager.getContext(false);
        context.setConfigLocation(log4jConfiguration.toUri());

        LOGGER.debug("reconfig from code"); // Should be logged
        LOGGER.info("shutdown"); // Should be logged with [OUTER] prefix
    }
}
