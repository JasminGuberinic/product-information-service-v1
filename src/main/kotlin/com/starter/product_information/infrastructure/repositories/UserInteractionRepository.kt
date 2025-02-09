package com.starter.product_information.infrastructure.repositories

import com.starter.product_information.infrastructure.entities.UserInteractionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserInteractionRepository : JpaRepository<UserInteractionEntity, Long> {
    fun findByUserId(userId: Long): List<UserInteractionEntity>
    fun findByUserIdAndProductId(userId: Long, productId: Long): List<UserInteractionEntity>
    fun findByProductId(productId: Long): List<UserInteractionEntity>
}