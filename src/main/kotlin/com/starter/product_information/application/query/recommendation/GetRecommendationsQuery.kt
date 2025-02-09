package com.starter.product_information.application.query.recommendation

data class GetRecommendationsQuery(
    val userId: Long,
    val limit: Int = 5
)