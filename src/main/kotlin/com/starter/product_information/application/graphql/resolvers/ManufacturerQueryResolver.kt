package com.starter.product_information.application.graphql.resolvers

import com.starter.product_information.domain.model.manufacturer.ManufacturerType
import com.starter.product_information.infrastructure.entities.ManufacturerEntity
import com.starter.product_information.infrastructure.entities.ProductEntity
import com.starter.product_information.infrastructure.repositories.ManufacturerRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.kickstart.tools.GraphQLResolver
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.slf4j.LoggerFactory

@Component
class ManufacturerQueryResolver : GraphQLQueryResolver {
    private val logger = LoggerFactory.getLogger(ManufacturerQueryResolver::class.java)

    @Autowired
    private lateinit var manufacturerRepository: ManufacturerRepository

    fun manufacturer(id: Long): ManufacturerEntity? {
        logger.info("Fetching manufacturer with ID: $id")
        return manufacturerRepository.findById(id).orElse(null)
    }

    fun manufacturers(): List<ManufacturerEntity> {
        logger.info("Fetching all manufacturers")
        return manufacturerRepository.findAll()
    }

    fun manufacturersByType(type: String): List<ManufacturerEntity> {
        logger.info("Fetching manufacturers of type: $type")
        return manufacturerRepository.findByManufacturerType(ManufacturerType.valueOf(type))
    }
}