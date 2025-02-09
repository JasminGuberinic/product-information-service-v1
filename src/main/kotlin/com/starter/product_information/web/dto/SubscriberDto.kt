package com.starter.product_information.web.dto

import java.time.LocalDateTime

data class SubscriberDto(
    val id: String? = null,
    val email: String,
    val subscriptionDate: LocalDateTime = LocalDateTime.now(),
    val notificationPreferences: Set<String> = emptySet()
)