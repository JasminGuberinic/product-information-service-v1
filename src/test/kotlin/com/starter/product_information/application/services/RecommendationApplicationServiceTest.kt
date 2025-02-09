package com.starter.product_information.application.service

import com.starter.product_information.domain.model.userInteraction.InteractionType
import com.starter.product_information.domain.model.userInteraction.UserInteraction
import com.starter.product_information.infrastructure.entities.UserInteractionEntity
import com.starter.product_information.infrastructure.repositories.UserInteractionRepository
import com.starter.product_information.domain.services.RecommendationDomainService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class RecommendationApplicationServiceTest {

    private lateinit var recommendationApplicationService: RecommendationApplicationService

    @Mock
    private lateinit var userInteractionRepository: UserInteractionRepository

    @Mock
    private lateinit var recommendationDomainService: RecommendationDomainService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        recommendationApplicationService = RecommendationApplicationService(
            userInteractionRepository,
            recommendationDomainService
        )
    }

    @Test
    fun `should record user interaction`() {
        // Given
        val userId = 1L
        val productId = 101L
        val interactionType = InteractionType.VIEW

        // When
        recommendationApplicationService.recordInteraction(userId, productId, interactionType)

        // Then
        verify(userInteractionRepository).save(any(UserInteractionEntity::class.java))
    }

    @Test
    fun `should get recommendations`() {
        // Given
        val userId = 1L
        val expectedRecommendations = listOf(201L, 202L)
        val emptyList = emptyList<UserInteraction>()

        `when`(userInteractionRepository.findByUserId(userId)).thenReturn(emptyList())
        `when`(userInteractionRepository.findAll()).thenReturn(emptyList())
        `when`(recommendationDomainService.calculateRecommendations(emptyList(), emptyList(), 5))
            .thenReturn(expectedRecommendations)

        // When
        val result = recommendationApplicationService.getRecommendations(userId)

        // Then
        assertEquals(expectedRecommendations, result)
    }
}