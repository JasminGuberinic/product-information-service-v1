package com.starter.product_information.domain.model.userInteraction

import java.time.LocalDateTime

data class UserInteraction(
    val id: Long? = null,
    val userId: Long,
    val productId: Long,
    val interactionType: InteractionType,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val rating: Int? = null
) {
    companion object {
        fun create(userId: Long, productId: Long, type: InteractionType, rating: Int? = null): UserInteraction {
            return UserInteraction(
                userId = userId,
                productId = productId,
                interactionType = type,
                rating = rating
            )
        }
    }
}