package com.starter.product_information.integration

import com.starter.product_information.domain.entities.manufacturer.ManufacturerType
import com.starter.product_information.domain.entities.product.DistributionChannel
import com.starter.product_information.domain.entities.supplier.SupplierClassification
import com.starter.product_information.domain.entities.supplier.SupplierType
import com.starter.product_information.infrastructure.entities.LocalContactInfoEntity
import com.starter.product_information.infrastructure.entities.ManufacturerEntity
import com.starter.product_information.infrastructure.entities.ProductEntity
import com.starter.product_information.infrastructure.entities.SupplierEntity
import com.starter.product_information.infrastructure.repositories.ProductRepository
import com.starter.product_information.web.controllers.ProductController
import com.starter.product_information.web.dto.LocalContactInfoDto
import com.starter.product_information.web.dto.ManufacturerDto
import com.starter.product_information.web.dto.ProductDto
import com.starter.product_information.web.dto.SupplierDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
class ProductControllerIntegrationTest {

    @Autowired
    lateinit var productController: ProductController

    @Autowired
    lateinit var productRepository: ProductRepository // Repo za interakciju sa bazom

    @Test
    @Transactional
    fun `should create product and save to database`() {
        val supplierDto = SupplierDto(
            type = SupplierType.LOCAL,
            name = "Test Supplier",
            qualityRating = 5,
            averagePrice = 50.0,
            averageDeliveryTime = 10,
            averageOrderQuantity = 100,
            isCertified = true,
            supplierClassification = SupplierClassification.Premium,
            localContactInfo = LocalContactInfoDto(
                phoneNumber = "123456789",
                email = "supplier@test.com",
                address = "123 Test St"
            )
        )

        val manufacturerDto = ManufacturerDto(
            type = ManufacturerType.OEM,
            name = "Test Manufacturer",
            country = "Country",
            oemCode = "OEM123"
        )

        val productDto = ProductDto(
            name = "Test Product",
            description = "Test Description",
            price = 99.99,
            supplier = supplierDto,
            manufacturer = manufacturerDto,
            design = "original",
            customization = false,
            productionQuantity = 1100,
            brand = "Test Brand",
            distributionChannel = DistributionChannel.ONLINE,
            countryOfOrigin = "Country",
            certificates = listOf("Cert1", "Cert2"),
            deliveryTime = 6,
            companyCountry = "Country"
        )

        val response = productController.createProduct(productDto)

        assertEquals("Product created successfully", response.body)

        val savedProduct = productRepository.findByName("Test Product")
        assertNotNull(savedProduct)
        assertEquals("Test Product", savedProduct?.name)
        assertEquals("Test Description", savedProduct?.description)
        assertEquals(99.99, savedProduct?.price)
    }

    @Test
    @Transactional
    fun `should return product by id`() {
        val supplier = SupplierEntity(
            name = "Test Supplier",
            supplierType = SupplierType.LOCAL,
            supplierClassification = SupplierClassification.Premium,
            qualityRating = 5,
            averagePrice = 50.0,
            averageDeliveryTime = 10,
            averageOrderQuantity = 100,
            isCertified = true,
            localContactInfo = LocalContactInfoEntity(
                phoneNumber = "123456789",
                email = "supplier@test.com",
                address = "123 Test St"
            )
        )

        val manufacturer = ManufacturerEntity(
            name = "Test Manufacturer",
            country = "Country",
            manufacturerType = ManufacturerType.OEM,
            oemCode = "OEM123"
        )

        val product = ProductEntity(
            name = "Test Product",
            description = "Test Description",
            price = 99.99,
            supplier = supplier,
            manufacturer = manufacturer,
            design = "Modern",
            customization = true,
            productionQuantity = 1000,
            brand = "Test Brand",
            distributionChannel = DistributionChannel.ONLINE,
            countryOfOrigin = "Country",
            certificates = listOf("Cert1", "Cert2"),
            deliveryTime = 30
        )

        val savedProduct = productRepository.save(product)

        val response = productController.getProduct(savedProduct.id)

        val productReturned = response.body
        assertNotNull(productReturned)
        assertEquals("Test Product", productReturned?.name)
        assertEquals("Test Description", productReturned?.description)
        assertEquals(99.99, productReturned?.price)
    }
}
