package com.starter.product_information.application.commands.create_product

import com.starter.product_information.application.events.ProductCreatedEvent
import com.starter.product_information.application.service.ProductService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class CreateProductCommandHandler(
    private val productService: ProductService,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun handle(command: CreateProductCommand) {
        val product = productService.createProduct(command.name, command.description, command.price)

        // Generisanje događaja
        val event = ProductCreatedEvent(product.id, product.name, product.description, product.price)
        applicationEventPublisher.publishEvent(event)
    }
}