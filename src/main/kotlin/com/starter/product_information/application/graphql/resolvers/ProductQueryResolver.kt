package com.starter.product_information.application.graphql.resolvers

import com.starter.product_information.infrastructure.entities.ProductEntity
import com.starter.product_information.infrastructure.repositories.ProductRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProductQueryResolver : GraphQLQueryResolver {

    private val logger = LoggerFactory.getLogger(ProductQueryResolver::class.java)

    @Autowired
    private lateinit var productRepository: ProductRepository

    fun product(id: Long): ProductEntity? {
        return try {
            val product = productRepository.findById(id).orElse(null)

            if (product == null) {
                logger.warn("Product with ID: $id not found")
            }

            product
        } catch (e: Exception) {
            logger.error("Error getting the product with ID: $id", e)
            null
        }
    }

    fun products(): List<ProductEntity> {
        return try {
            productRepository.findAll()
        } catch (e: Exception) {
            logger.error("Error getting the product with ID", e)
            emptyList()
        }
    }
}
