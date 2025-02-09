package com.starter.product_information.application.commands.subscriber

import com.starter.product_information.domain.model.notification.Subscriber
import com.starter.product_information.infrastructure.entities.SubscriberEntity
import com.starter.product_information.infrastructure.repositories.SubscriberRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class AddSubscriberCommandHandler(
    private val subscriberRepository: SubscriberRepository
) {
    fun handle(command: AddSubscriberCommand): AddSubscriberResponse {
        val subscriber = SubscriberEntity(
            id = UUID.randomUUID().toString(),
            email = command.email,
            subscriptionDate = LocalDateTime.now()
        )

        val savedSubscriber = subscriberRepository.save(subscriber)
        return AddSubscriberResponse(savedSubscriber)
    }
}

data class AddSubscriberResponse(
    val subscriber: SubscriberEntity
)