package com.starter.product_information.domain.services

import com.starter.product_information.domain.model.userInteraction.UserInteraction
import org.springframework.stereotype.Service

@Service
class RecommendationDomainService {

    fun calculateRecommendations(
        userInteractions: List<UserInteraction>,
        allUserInteractions: List<UserInteraction>,
        limit: Int = 5
    ): List<Long> {
        if (userInteractions.isEmpty()) {
            return getMostPopularProducts(allUserInteractions, limit)
        }
        return getCollaborativeFilteringRecommendations(
            userInteractions,
            allUserInteractions,
            limit
        )
    }

    private fun getMostPopularProducts(allInteractions: List<UserInteraction>, limit: Int): List<Long> {
        return allInteractions
            .groupBy { it.productId }
            .mapValues { it.value.size }
            .entries
            .sortedByDescending { it.value }
            .take(limit)
            .map { it.key }
    }

    private fun getCollaborativeFilteringRecommendations(
        userInteractions: List<UserInteraction>,
        allInteractions: List<UserInteraction>,
        limit: Int
    ): List<Long> {
        val userId = userInteractions.first().userId
        val userProducts = userInteractions.map { it.productId }.toSet()

        val similarUsers = findSimilarUsers(userId, userProducts, allInteractions)

        return similarUsers
            .flatMap { similarUserId ->
                allInteractions.filter { it.userId == similarUserId }
            }
            .map { it.productId }
            .distinct()
            .filter { it !in userProducts }
            .take(limit)
    }

    private fun findSimilarUsers(
        userId: Long,
        userProducts: Set<Long>,
        allInteractions: List<UserInteraction>
    ): List<Long> {
        return allInteractions
            .map { it.userId }
            .distinct()
            .filter { it != userId }
            .map { otherId ->
                val similarity = calculateUserSimilarity(userProducts,
                    allInteractions.filter { it.userId == otherId }.map { it.productId }.toSet()
                )
                Pair(otherId, similarity)
            }
            .sortedByDescending { it.second }
            .take(5)
            .map { it.first }
    }

    private fun calculateUserSimilarity(user1Products: Set<Long>, user2Products: Set<Long>): Double {
        val commonProducts = user1Products.intersect(user2Products)
        if (commonProducts.isEmpty()) return 0.0

        // Jaccard similarity coefficient
        return commonProducts.size.toDouble() / (user1Products.size + user2Products.size - commonProducts.size)
    }
}