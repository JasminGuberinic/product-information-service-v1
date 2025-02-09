package com.starter.product_information.application.graphql.resolvers

import com.starter.product_information.infrastructure.entities.*
import com.starter.product_information.infrastructure.repositories.ProductRepository
import com.starter.product_information.infrastructure.repositories.SupplierRepository
import com.starter.product_information.infrastructure.repositories.ManufacturerRepository
import com.starter.product_information.domain.model.product.DistributionChannel
import com.starter.product_information.application.graphql.input.ProductPriceInput
import com.starter.product_information.application.graphql.input.SimpleProductInput
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

@Component
class ProductMutationResolver : GraphQLMutationResolver {

    private val logger = LoggerFactory.getLogger(ProductMutationResolver::class.java)

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var supplierRepository: SupplierRepository

    @Autowired
    private lateinit var manufacturerRepository: ManufacturerRepository

    private fun getDefaultSupplier(): SupplierEntity {
        return supplierRepository.findById(1L).orElseThrow {
            RuntimeException("Default supplier not found")
        }
    }

    private fun getDefaultManufacturer(): ManufacturerEntity {
        return manufacturerRepository.findById(1L).orElseThrow {
            RuntimeException("Default manufacturer not found")
        }
    }

    fun addSimpleProduct(input: SimpleProductInput): ProductEntity {
        logger.info("Adding simple product: ${input.name}")

        try {
            val product = ProductEntity(
                name = input.name,
                description = input.description,
                price = 0.0,
                supplier = getDefaultSupplier(),
                manufacturer = getDefaultManufacturer(),
                productionQuantity = 0,
                design = "Default",
                customization = false,
                brand = "Default",
                distributionChannel = DistributionChannel.DIRECT,
                countryOfOrigin = "Unknown",
                certificates = emptyList(),
                deliveryTime = 0
            )

            return productRepository.save(product)
        } catch (e: Exception) {
            logger.error("Error creating simple product", e)
            throw RuntimeException("Could not create product: ${e.message}")
        }
    }

    fun addProductWithPrice(input: ProductPriceInput): ProductEntity {
        logger.info("Adding product with price: ${input.name}")

        try {
            val product = ProductEntity(
                name = input.name,
                description = input.description,
                price = input.price,
                supplier = getDefaultSupplier(),
                manufacturer = getDefaultManufacturer(),
                productionQuantity = input.quantity,
                design = "Default",
                customization = false,
                brand = "Default",
                distributionChannel = DistributionChannel.DIRECT,
                countryOfOrigin = "Unknown",
                certificates = emptyList(),
                deliveryTime = 0
            )

            return productRepository.save(product)
        } catch (e: Exception) {
            logger.error("Error creating product with price", e)
            throw RuntimeException("Could not create product: ${e.message}")
        }
    }
}

//#
//mutation {
//    addSimpleProduct(input: {
//        name: "Test Proizvod"
//        description: "Ovo je test proizvod"
//    }) {
//        id
//        name
//        description
//    }
//}
//
//#
//mutation {
//    addProductWithPrice(input: {
//        name: "Test Proizvod sa Cijenom"
//        description: "Ovo je proizvod sa cijenom"
//        price: 99.99
//        quantity: 10
//    }) {
//        id
//        name
//        description
//        price
//        productionQuantity
//    }
//}