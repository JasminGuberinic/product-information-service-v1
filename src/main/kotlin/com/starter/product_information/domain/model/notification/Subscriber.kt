package com.starter.product_information.domain.model.notification

import java.time.LocalDateTime
import java.util.*

data class Subscriber(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val subscriptionDate: LocalDateTime = LocalDateTime.now(),
    val notificationPreferences: Set<String> = setOf("ALL") // mogu biti različiti tipovi notifikacija
)