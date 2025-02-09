package com.starter.product_information.application.mappers


import com.starter.product_information.domain.model.userInteraction.UserInteraction
import com.starter.product_information.domain.model.userInteraction.InteractionType as EntityInteractionType
import com.starter.product_information.infrastructure.entities.UserInteractionEntity

fun UserInteractionEntity.toDomain(): UserInteraction {
    return UserInteraction(
        id = this.id,
        userId = this.userId,
        productId = this.productId,
        interactionType = this.interactionType.toDomain(),
        timestamp = this.timestamp,
        rating = this.rating
    )
}

fun UserInteraction.toEntity(): UserInteractionEntity {
    return UserInteractionEntity(
        id = this.id,
        userId = this.userId,
        productId = this.productId,
        interactionType = this.interactionType.toEntity(),
        timestamp = this.timestamp,
        rating = this.rating
    )
}

fun EntityInteractionType.toDomain(): EntityInteractionType {
    return when (this) {
        EntityInteractionType.VIEW -> EntityInteractionType.VIEW
        EntityInteractionType.LIKE -> EntityInteractionType.LIKE
        EntityInteractionType.PURCHASE -> EntityInteractionType.PURCHASE
    }
}

fun EntityInteractionType.toEntity(): EntityInteractionType {
    return when (this) {
        EntityInteractionType.VIEW -> EntityInteractionType.VIEW
        EntityInteractionType.LIKE -> EntityInteractionType.LIKE
        EntityInteractionType.PURCHASE -> EntityInteractionType.PURCHASE
    }
}

// Collection extensions for convenience
fun List<UserInteractionEntity>.toDomain(): List<UserInteraction> = this.map { it.toDomain() }
fun List<UserInteraction>.toEntity(): List<UserInteractionEntity> = this.map { it.toEntity() }