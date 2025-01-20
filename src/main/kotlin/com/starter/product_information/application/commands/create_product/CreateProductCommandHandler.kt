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
        val product = command.productDto.toDomain()
        val savedProduct = productService.saveProduct(product)

        // Generate event
        val event = ProductCreatedEvent(savedProduct.id, savedProduct.name, savedProduct.description, savedProduct.price)
        applicationEventPublisher.publishEvent(event)
    }
}