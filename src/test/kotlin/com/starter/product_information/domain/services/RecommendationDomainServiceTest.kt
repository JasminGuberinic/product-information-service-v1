package com.starter.product_information.domain.services

import com.starter.product_information.domain.model.userInteraction.InteractionType
import com.starter.product_information.domain.model.userInteraction.UserInteraction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RecommendationDomainServiceTest {

    private lateinit var recommendationDomainService: RecommendationDomainService
    private val userId1 = 1L
    private val userId2 = 2L
    private val userId3 = 3L
    private val productId1 = 101L
    private val productId2 = 102L
    private val productId3 = 103L

    @BeforeEach
    fun setup() {
        recommendationDomainService = RecommendationDomainService()
    }

    @Test
    @DisplayName("Should return most popular products when user has no interactions")
    fun `calculateRecommendations returns most popular products for new user`() {
        // Given
        val userInteractions = emptyList<UserInteraction>()
        val allInteractions = listOf(
            createInteraction(userId2, productId1, InteractionType.VIEW),
            createInteraction(userId2, productId1, InteractionType.PURCHASE),
            createInteraction(userId3, productId1, InteractionType.LIKE),
            createInteraction(userId2, productId2, InteractionType.VIEW),
            createInteraction(userId3, productId2, InteractionType.VIEW)
        )

        // When
        val recommendations = recommendationDomainService.calculateRecommendations(userInteractions, allInteractions, 2)

        // Then
        assertEquals(2, recommendations.size)
        assertEquals(productId1, recommendations[0]) // Most interactions
        assertEquals(productId2, recommendations[1]) // Second most interactions
    }

    @Test
    @DisplayName("Should return collaborative filtering recommendations for existing user")
    fun `calculateRecommendations returns collaborative filtering recommendations for existing user`() {
        // Given
        val userInteractions = listOf(
            createInteraction(userId1, productId1, InteractionType.VIEW),
            createInteraction(userId1, productId2, InteractionType.LIKE)
        )

        val allInteractions = userInteractions + listOf(
            createInteraction(userId2, productId1, InteractionType.VIEW),
            createInteraction(userId2, productId2, InteractionType.LIKE),
            createInteraction(userId2, productId3, InteractionType.PURCHASE), // Similar user liked this
            createInteraction(userId3, productId1, InteractionType.VIEW)
        )

        // When
        val recommendations = recommendationDomainService.calculateRecommendations(userInteractions, allInteractions)

        // Then
        assertTrue(recommendations.contains(productId3))
        assertTrue(recommendations.none { it == productId1 || it == productId2 }) // Should not recommend products user already interacted with
    }

    @Test
    @DisplayName("Should respect limit parameter")
    fun `calculateRecommendations respects limit parameter`() {
        // Given
        val userInteractions = listOf(
            createInteraction(userId1, productId1, InteractionType.VIEW)
        )

        val allInteractions = userInteractions + listOf(
            createInteraction(userId2, productId1, InteractionType.VIEW),
            createInteraction(userId2, productId2, InteractionType.LIKE),
            createInteraction(userId2, productId3, InteractionType.PURCHASE),
            createInteraction(userId3, productId1, InteractionType.VIEW)
        )

        // When
        val recommendations = recommendationDomainService.calculateRecommendations(userInteractions, allInteractions, 1)

        // Then
        assertEquals(1, recommendations.size)
    }

    @Test
    @DisplayName("Should not recommend products user already interacted with")
    fun `calculateRecommendations excludes products user already interacted with`() {
        // Given
        val userInteractions = listOf(
            createInteraction(userId1, productId1, InteractionType.VIEW),
            createInteraction(userId1, productId2, InteractionType.PURCHASE)
        )

        val allInteractions = userInteractions + listOf(
            createInteraction(userId2, productId1, InteractionType.VIEW),
            createInteraction(userId2, productId2, InteractionType.PURCHASE),
            createInteraction(userId2, productId3, InteractionType.VIEW),
            createInteraction(userId3, productId1, InteractionType.LIKE)
        )

        // When
        val recommendations = recommendationDomainService.calculateRecommendations(userInteractions, allInteractions)

        // Then
        assertTrue(recommendations.none { it == productId1 || it == productId2 })
        assertTrue(recommendations.contains(productId3))
    }

    private fun createInteraction(
        userId: Long,
        productId: Long,
        interactionType: InteractionType,
        rating: Int? = null
    ): UserInteraction {
        return UserInteraction.create(userId, productId, interactionType, rating)
    }
}