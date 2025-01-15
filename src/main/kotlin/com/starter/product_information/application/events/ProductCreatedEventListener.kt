package com.starter.product_information.application.events

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ProductCreatedEventListener {

    @EventListener
    fun onProductCreated(event: ProductCreatedEvent) {
        println("Product created: ${event.name}, Price: ${event.price}")
    }
}
