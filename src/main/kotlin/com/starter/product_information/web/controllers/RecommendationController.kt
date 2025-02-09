package com.starter.product_information.web.controllers

import com.starter.product_information.application.RequestHandler
import com.starter.product_information.application.commands.interaction.RecordInteractionCommand
import com.starter.product_information.application.query.recommendation.GetRecommendationsQuery
import com.starter.product_information.domain.model.userInteraction.InteractionType
import com.starter.product_information.web.dto.RecommendationResponseDto
import com.starter.product_information.web.dto.RecordInteractionDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recommendations")
class RecommendationController(
    private val requestHandler: RequestHandler
) {
    @PostMapping("/interactions")
    fun recordInteraction(@RequestBody dto: RecordInteractionDto): ResponseEntity<Unit> {
        val command = RecordInteractionCommand(
            userId = dto.userId,
            productId = dto.productId,
            interactionType = InteractionType.valueOf(dto.interactionType),
            rating = dto.rating
        )
        requestHandler.handleCommand(command)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/users/{userId}")
    fun getRecommendations(
        @PathVariable userId: Long,
        @RequestParam(defaultValue = "5") limit: Int
    ): ResponseEntity<RecommendationResponseDto> {
        val query = GetRecommendationsQuery(userId, limit)
        val recommendations = requestHandler.handleQuery(query) as RecommendationResponseDto
        return ResponseEntity.ok(recommendations)
    }
}