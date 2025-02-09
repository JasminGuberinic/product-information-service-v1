package com.starter.product_information.web.controllers


import com.starter.product_information.application.RequestHandler
import com.starter.product_information.application.commands.subscriber.AddSubscriberCommand
import com.starter.product_information.application.commands.subscriber.AddSubscriberResponse
import com.starter.product_information.application.query.subscriber.GetAllSubscribersQuery
import com.starter.product_information.application.mappers.toSubscriberDto
import com.starter.product_information.infrastructure.entities.SubscriberEntity
import com.starter.product_information.web.dto.SubscriberDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/subscribers")
class SubscriberController(
    private val requestHandler: RequestHandler
) {
    @PostMapping
    fun addSubscriber(@RequestBody email: String): ResponseEntity<String> {
        val command = AddSubscriberCommand(email)
        requestHandler.handleCommand(command)
        return ResponseEntity.ok("Subscriber added successfully")
    }

    @GetMapping
    fun getSubscribers(): ResponseEntity<List<String>> {
        val query = GetAllSubscribersQuery()
        val subscribers = requestHandler.handleQuery(query) as List<String>
        return ResponseEntity.ok(subscribers)
    }
}