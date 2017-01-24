package no.ezand.jottakloud

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import org.slf4j.LoggerFactory

object LogbackLogging {
    const val loggerPrefix = "no.ezand.jottakloud"

    fun applyLogLevel(level: String) {
        val loggers = LoggerFactory.getILoggerFactory() as LoggerContext
        loggers.loggerList
                .filter { logger -> logger.name.startsWith(loggerPrefix) }
                .forEach { logger ->
                    logger.level = Level.toLevel(level)
                }
    }
}