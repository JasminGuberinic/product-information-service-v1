package com.starter.product_information.infrastructure.entities

import com.starter.product_information.domain.model.userInteraction.InteractionType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_interactions")
data class UserInteractionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val productId: Long,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val interactionType: InteractionType,

    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = true)
    val rating: Int? = null
)