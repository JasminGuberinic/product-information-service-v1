package com.starter.product_information.infrastructure.entities

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "subscribers")
data class SubscriberEntity(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val subscriptionDate: LocalDateTime = LocalDateTime.now(),

    @ElementCollection
    @CollectionTable(
        name = "subscriber_preferences",
        joinColumns = [JoinColumn(name = "subscriber_id")]
    )
    @Column(name = "preference")
    val notificationPreferences: Set<String> = setOf("ALL")
)