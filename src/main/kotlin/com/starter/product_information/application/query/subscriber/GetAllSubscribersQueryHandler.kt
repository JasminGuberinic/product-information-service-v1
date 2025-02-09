package com.starter.product_information.application.query.subscriber

import com.starter.product_information.infrastructure.entities.SubscriberEntity
import com.starter.product_information.infrastructure.repositories.SubscriberRepository
import org.springframework.stereotype.Component

@Component
class GetAllSubscribersQueryHandler(
    private val subscriberRepository: SubscriberRepository
) {
    fun handle(query: GetAllSubscribersQuery): List<SubscriberEntity> {
        return subscriberRepository.findAll()
    }
}