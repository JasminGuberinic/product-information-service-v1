package com.starter.product_information.application.query.recommendation

import com.starter.product_information.application.service.RecommendationApplicationService
import com.starter.product_information.web.dto.RecommendationResponseDto
import org.springframework.stereotype.Component

@Component
class GetRecommendationsQueryHandler(
    private val recommendationApplicationService: RecommendationApplicationService
) {
    fun handle(query: GetRecommendationsQuery): RecommendationResponseDto {
        val recommendations = recommendationApplicationService.getRecommendations(
            query.userId,
            query.limit
        )
        return RecommendationResponseDto(recommendations)
    }
}