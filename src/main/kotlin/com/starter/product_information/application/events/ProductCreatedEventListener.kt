package com.starter.product_information.application.events

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.beans.factory.annotation.Value
import org.slf4j.LoggerFactory

@Component
class ProductCreatedEventListener(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val logger = LoggerFactory.getLogger(ProductCreatedEventListener::class.java)

    @Value("\${app.kafka.enabled:false}")
    private var kafkaEnabled: Boolean = false

    @Value("\${app.kafka.topic.product-created:product-created}")
    private lateinit var topic: String

    @EventListener
    fun onProductCreated(event: ProductCreatedEvent) {
        logger.info("Product created event received: ${event.name}")

        if (kafkaEnabled) {
            try {
                kafkaTemplate.send(topic, event.productId.toString(), event).get()
                logger.info("Event successfully sent to Kafka topic: $topic")
            } catch (e: Exception) {
                logger.error("Failed to send event to Kafka", e)
            }
        } else {
            logger.info("Kafka is disabled. Skipping event publishing.")
        }
    }
}
