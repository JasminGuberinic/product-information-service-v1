package com.starter.product_information.application.graphql.resolvers

import com.starter.product_information.infrastructure.entities.ProductEntity
import graphql.kickstart.tools.GraphQLResolver
import org.springframework.stereotype.Component

/**
 * Resolver za polja Supplier-a koja možda trebaju dodatno računanje ili dohvatanje
 */
@Component
class SupplierResolver : GraphQLResolver<ProductEntity> {
    fun qualityRating(product: ProductEntity): Float {
        return product.supplier.qualityRating.toFloat()
    }
}