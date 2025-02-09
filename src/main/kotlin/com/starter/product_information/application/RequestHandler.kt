package com.starter.product_information.application

import com.starter.product_information.application.commands.create_product.CreateProductCommand
import com.starter.product_information.application.commands.create_product.CreateProductCommandHandler
import com.starter.product_information.application.commands.interaction.RecordInteractionCommand
import com.starter.product_information.application.commands.interaction.RecordInteractionCommandHandler
import com.starter.product_information.application.commands.subscriber.AddSubscriberCommand
import com.starter.product_information.application.commands.subscriber.AddSubscriberCommandHandler
import com.starter.product_information.application.query.get_product.GetProductQuery
import com.starter.product_information.application.query.get_product.GetProductQueryHandler
import com.starter.product_information.application.query.recommendation.GetRecommendationsQuery
import com.starter.product_information.application.query.recommendation.GetRecommendationsQueryHandler
import com.starter.product_information.application.query.subscriber.GetAllSubscribersQuery
import com.starter.product_information.application.query.subscriber.GetAllSubscribersQueryHandler
import org.springframework.stereotype.Component

@Component
class RequestHandler(
    private val createProductCommandHandler: CreateProductCommandHandler,
    private val getProductQueryHandler: GetProductQueryHandler,
    private val recordInteractionCommandHandler: RecordInteractionCommandHandler,
    private val getRecommendationsQueryHandler: GetRecommendationsQueryHandler,
    private val addSubscriberCommandHandler: AddSubscriberCommandHandler,
    private val getAllSubscribersQueryHandler: GetAllSubscribersQueryHandler
) {

    // Handler za komande
    fun <T> handleCommand(command: T) {
        when (command) {
            is CreateProductCommand -> createProductCommandHandler.handle(command)
            is RecordInteractionCommand -> recordInteractionCommandHandler.handle(command)
            is AddSubscriberCommand -> addSubscriberCommandHandler.handle(command)
            else -> throw IllegalArgumentException("Unsupported command type")
        }
    }

    // Handler za upite
    fun <T> handleQuery(query: T): Any? {
        return when (query) {
            is GetProductQuery -> getProductQueryHandler.handle(query)
            is GetRecommendationsQuery -> getRecommendationsQueryHandler.handle(query)
            is GetAllSubscribersQuery -> getAllSubscribersQueryHandler.handle(query)
            else -> throw IllegalArgumentException("Unsupported query type")
        }
    }
}