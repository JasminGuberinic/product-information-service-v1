package com.starter.product_information.application.mappers

import com.starter.product_information.infrastructure.entities.SubscriberEntity
import com.starter.product_information.web.dto.SubscriberDto

fun SubscriberEntity.toSubscriberDto() = SubscriberDto(
    id = id,
    email = email,
    subscriptionDate = subscriptionDate,
    notificationPreferences = notificationPreferences
)