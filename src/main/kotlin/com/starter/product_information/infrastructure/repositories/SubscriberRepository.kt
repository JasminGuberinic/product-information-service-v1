package com.starter.product_information.infrastructure.repositories

import com.starter.product_information.infrastructure.entities.SubscriberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriberRepository : JpaRepository<SubscriberEntity, String> {
    fun findByEmail(email: String): SubscriberEntity?
}