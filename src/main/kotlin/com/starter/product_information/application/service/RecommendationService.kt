package com.starter.product_information.application.service

import com.starter.product_information.application.mappers.toDomain
import com.starter.product_information.application.mappers.toEntity
import com.starter.product_information.domain.model.userInteraction.InteractionType
import com.starter.product_information.domain.model.userInteraction.UserInteraction
import com.starter.product_information.domain.services.RecommendationDomainService
import com.starter.product_information.infrastructure.repositories.UserInteractionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecommendationApplicationService(
    private val userInteractionRepository: UserInteractionRepository,
    private val recommendationDomainService: RecommendationDomainService
) {
    @Transactional
    fun recordInteraction(userId: Long, productId: Long, interactionType: InteractionType, rating: Int? = null) {
        val interaction = UserInteraction.create(userId, productId, interactionType, rating)
        userInteractionRepository.save(interaction.toEntity())
    }

    fun getRecommendations(userId: Long, limit: Int = 5): List<Long> {
        val userInteractions = userInteractionRepository.findByUserId(userId).toDomain()
        val allInteractions = userInteractionRepository.findAll().toDomain()

        return recommendationDomainService.calculateRecommendations(
            userInteractions,
            allInteractions,
            limit
        )
    }
}