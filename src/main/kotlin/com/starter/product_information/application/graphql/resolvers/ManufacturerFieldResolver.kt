package com.starter.product_information.application.graphql.resolvers

import com.starter.product_information.infrastructure.entities.ProductEntity
import graphql.kickstart.tools.GraphQLResolver
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class ManufacturerFieldResolver : GraphQLResolver<ProductEntity> {
    private val logger = LoggerFactory.getLogger(ManufacturerFieldResolver::class.java)

    fun country(product: ProductEntity): String {
        logger.info("Resolving manufacturer country for product: ${product.id}")
        return product.manufacturer?.country ?: "Unknown"
    }

    fun type(product: ProductEntity): String {
        logger.info("Resolving manufacturer type for product: ${product.id}")
        return product.manufacturer?.manufacturerType?.name ?: "Unknown"
    }

    fun name(product: ProductEntity): String {
        logger.info("Resolving manufacturer name for product: ${product.id}")
        return product.manufacturer?.name ?: "Unknown"
    }
}