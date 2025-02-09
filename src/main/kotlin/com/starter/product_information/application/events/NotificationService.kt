package com.starter.product_information.application.events

import com.starter.product_information.infrastructure.repositories.SubscriberRepository
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val subscriberRepository: SubscriberRepository
) {
    @Async
    @EventListener
    fun handleFileUpload(event: FileUploadedEvent) {
        val subscribers = subscriberRepository.findAll()

        subscribers.forEach { subscriber ->
            sendNotification(
                subscriber.email,
                "New file uploaded: ${event.filename}",
                """
                Hello!
                
                A new file has been uploaded to our system:
                Filename: ${event.filename}
                Type: ${event.contentType}
                Upload time: ${event.uploadedAt}
                
                Best regards,
                Your System
                """.trimIndent()
            )
        }
    }

    private fun sendNotification(email: String, subject: String, content: String) {
        //email simulation
        println("Sending email to: $email")
        println("Subject: $subject")
        println("Content: $content")
        println("------------------------")
    }
}