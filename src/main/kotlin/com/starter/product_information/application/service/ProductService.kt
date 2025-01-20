package com.starter.product_information.application.service

import com.starter.product_information.application.mappers.ProductMapper
import com.starter.product_information.domain.entities.product.Product
import com.starter.product_information.domain.services.ProductDomainService
import com.starter.product_information.infrastructure.entities.ProductEntity
import com.starter.product_information.infrastructure.repositories.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val manufacturerService: ManufacturerService,
    private val supplierService: SupplierService,
    private val productMapper: ProductMapper,
    private val productDomainService: ProductDomainService
) {

    @Transactional
    fun saveProduct(product: Product): ProductEntity {

        productDomainService.validateProduct(product)

        // Find or create related domain objects
        val supplier = supplierService.createSupplier(product)
        val manufacturer = manufacturerService.findOrCreateManufacturer(product)

        // Map domain product to entity
        val productEntity = productMapper.toEntity(product, supplier, manufacturer)

        // Save product entity to repository
        return productRepository.save(productEntity)
    }
}