package com.starter.product_information.application.commands.interaction

import com.starter.product_information.application.service.RecommendationApplicationService
import org.springframework.stereotype.Component

@Component
class RecordInteractionCommandHandler(
    private val recommendationApplicationService: RecommendationApplicationService
) {
    fun handle(command: RecordInteractionCommand) {
        recommendationApplicationService.recordInteraction(
            command.userId,
            command.productId,
            command.interactionType,
            command.rating
        )
    }
}