package com.starter.product_information.application.commands.interaction

import com.starter.product_information.domain.model.userInteraction.InteractionType

data class RecordInteractionCommand(
    val userId: Long,
    val productId: Long,
    val interactionType: InteractionType,
    val rating: Int?
)