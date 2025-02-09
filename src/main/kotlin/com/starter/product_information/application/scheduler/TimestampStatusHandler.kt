package com.starter.product_information.application.scheduler

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

/**
 * Utility class for handling Linux timestamps in status updates
 */
object TimestampStatusHandler {

    /**
     * Converts current time to Linux timestamp string
     */
    fun getCurrentTimestamp(): String =
        Instant.now().epochSecond.toString()

    /**
     * Calculates days between a Linux timestamp and current time
     * Returns 0 if timestamp is null or invalid
     */
    fun getDaysSinceUpdate(timestamp: String?): Long {
        if (timestamp == null) return 0

        return try {
            val lastUpdate = Instant.ofEpochSecond(timestamp.toLong())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            ChronoUnit.DAYS.between(lastUpdate, LocalDateTime.now())
        } catch (e: Exception) {
            0
        }
    }

    /**
     * Checks if the timestamp is older than specified days
     * Returns false if timestamp is null or invalid
     */
    fun isOlderThanDays(timestamp: String?, days: Long): Boolean {
        return getDaysSinceUpdate(timestamp) > days
    }
}