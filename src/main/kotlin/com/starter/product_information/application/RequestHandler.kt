package com.starter.product_information.application

import com.starter.product_information.application.commands.create_product.CreateProductCommand
import com.starter.product_information.application.commands.create_product.CreateProductCommandHandler
import com.starter.product_information.application.query.get_product.GetProductQuery
import com.starter.product_information.application.query.get_product.GetProductQueryHandler
import org.springframework.stereotype.Component

@Component
class RequestHandler(
    private val createProductCommandHandler: CreateProductCommandHandler,
    private val getProductQueryHandler: GetProductQueryHandler
) {

    // Handler za komande
    fun <T> handleCommand(command: T) {
        when (command) {
            is CreateProductCommand -> createProductCommandHandler.handle(command)
            else -> throw IllegalArgumentException("Unsupported command type")
        }
    }

    // Handler za upite
    fun <T> handleQuery(query: T): Any? {
        return when (query) {
            is GetProductQuery -> getProductQueryHandler.handle(query)
            else -> throw IllegalArgumentException("Unsupported query type")
        }
    }
}