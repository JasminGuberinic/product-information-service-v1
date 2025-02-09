package com.starter.product_information.application.service

import com.starter.product_information.application.mappers.ProductMapper
import com.starter.product_information.domain.model.contactInformation.LocalContactInfo
import com.starter.product_information.domain.model.manufacturer.ManufacturerType
import com.starter.product_information.domain.model.manufacturer.OEMManufacturer
import com.starter.product_information.domain.model.product.DistributionChannel
import com.starter.product_information.domain.model.product.Product
import com.starter.product_information.domain.model.supplier.LocalSupplier
import com.starter.product_information.domain.model.supplier.SupplierClassification
import com.starter.product_information.domain.model.supplier.SupplierType
import com.starter.product_information.domain.services.ProductDomainService
import com.starter.product_information.infrastructure.entities.LocalContactInfoEntity
import com.starter.product_information.infrastructure.entities.ManufacturerEntity
import com.starter.product_information.infrastructure.entities.ProductEntity
import com.starter.product_information.infrastructure.entities.SupplierEntity
import com.starter.product_information.infrastructure.repositories.ProductRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class ProductServiceTest {

    private lateinit var productService: ProductService

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var manufacturerService: ManufacturerService

    @Mock
    private lateinit var supplierService: SupplierService

    @Mock
    private lateinit var productMapper: ProductMapper

    @Mock
    private lateinit var productDomainService: ProductDomainService

    private lateinit var testProduct: Product
    private lateinit var testSupplier: LocalSupplier
    private lateinit var testManufacturer: OEMManufacturer
    private lateinit var testSupplierEntity: SupplierEntity
    private lateinit var testManufacturerEntity: ManufacturerEntity
    private lateinit var testProductEntity: ProductEntity

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        productService = ProductService(
            productRepository,
            manufacturerService,
            supplierService,
            productMapper,
            productDomainService
        )

        val localContactInfo = LocalContactInfo(
            phoneNumber = "123456789",
            email = "test@supplier.com",
            address = "Test Address 123"
        )

        testSupplier = LocalSupplier(
            name = "Test Supplier",
            qualityRating = 4,
            averagePrice = 100.0,
            averageDeliveryTime = 7,
            averageOrderQuantity = 1000,
            isCertified = true,
            supplierClassification = SupplierClassification.Standard,
            contactInfo = localContactInfo
        )

        testManufacturer = OEMManufacturer(
            name = "Test Manufacturer",
            country = "USA",
            oemCode = "TEST123"
        )

        testSupplierEntity = SupplierEntity(
            name = "Test Supplier",
            supplierType = SupplierType.LOCAL,
            supplierClassification = SupplierClassification.Standard,
            qualityRating = 4,
            averagePrice = 100.0,
            averageDeliveryTime = 7,
            averageOrderQuantity = 1000,
            isCertified = true,
            localContactInfo = LocalContactInfoEntity(
                phoneNumber = "123456789",
                email = "test@supplier.com",
                address = "Test Address 123"
            )
        )

        testManufacturerEntity = ManufacturerEntity(
            name = "Test Manufacturer",
            country = "USA",
            manufacturerType = ManufacturerType.OEM,
            oemCode = "TEST123"
        )

        testProduct = Product(
            id = 1L,
            name = "Test Product",
            description = "Test Description",
            supplier = testSupplier,
            manufacturer = testManufacturer,
            design = "original",
            customization = false,
            productionQuantity = 1000,
            brand = "Test Brand",
            distributionChannel = DistributionChannel.ONLINE,
            price = 99.99,
            countryOfOrigin = "USA",
            certificates = listOf("ISO9001"),
            deliveryTime = 30,
            companyCountry = "USA"
        )

        testProductEntity = ProductEntity(
            name = testProduct.name,
            description = testProduct.description,
            price = testProduct.price,
            supplier = testSupplierEntity,
            manufacturer = testManufacturerEntity,
            design = testProduct.design,
            customization = testProduct.customization,
            productionQuantity = testProduct.productionQuantity,
            brand = testProduct.brand,
            distributionChannel = testProduct.distributionChannel,
            countryOfOrigin = testProduct.countryOfOrigin,
            certificates = testProduct.certificates,
            deliveryTime = testProduct.deliveryTime,
            companyCountry = testProduct.companyCountry
        )
    }

    @Test
    @DisplayName("Should successfully save a valid product")
    fun `saveProduct successfully saves valid product`() {
        // Given
        doNothing().`when`(productDomainService).validateProduct(testProduct)
        `when`(supplierService.createSupplier(testProduct)).thenReturn(testSupplierEntity)
        `when`(manufacturerService.findOrCreateManufacturer(testProduct)).thenReturn(testManufacturerEntity)
        `when`(productMapper.toEntity(testProduct, testSupplierEntity, testManufacturerEntity)).thenReturn(testProductEntity)
        `when`(productRepository.save(testProductEntity)).thenReturn(testProductEntity)

        // When
        val result = productService.saveProduct(testProduct)

        // Then
        assertEquals(testProductEntity, result)
        verify(productDomainService).validateProduct(testProduct)
        verify(supplierService).createSupplier(testProduct)
        verify(manufacturerService).findOrCreateManufacturer(testProduct)
        verify(productMapper).toEntity(testProduct, testSupplierEntity, testManufacturerEntity)
        verify(productRepository).save(testProductEntity)
    }

    @Test
    @DisplayName("Should validate product before saving")
    fun `saveProduct validates product before saving`() {
        // Given
        doThrow(IllegalArgumentException("Invalid product")).`when`(productDomainService).validateProduct(testProduct)

        // When/Then
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            productService.saveProduct(testProduct)
        }

        verify(productDomainService).validateProduct(testProduct)
        verifyNoInteractions(supplierService, manufacturerService, productMapper, productRepository)
    }

    @Test
    @DisplayName("Should handle supplier creation failure")
    fun `saveProduct handles supplier creation failure`() {
        // Given
        doNothing().`when`(productDomainService).validateProduct(testProduct)
        `when`(supplierService.createSupplier(testProduct))
            .thenThrow(IllegalStateException("Failed to create supplier"))

        // When/Then
        org.junit.jupiter.api.assertThrows<IllegalStateException> {
            productService.saveProduct(testProduct)
        }

        verify(productDomainService).validateProduct(testProduct)
        verify(supplierService).createSupplier(testProduct)
        verifyNoInteractions(manufacturerService, productMapper, productRepository)
    }

    @Test
    @DisplayName("Should handle manufacturer creation failure")
    fun `saveProduct handles manufacturer creation failure`() {
        // Given
        doNothing().`when`(productDomainService).validateProduct(testProduct)
        `when`(supplierService.createSupplier(testProduct)).thenReturn(testSupplierEntity)
        `when`(manufacturerService.findOrCreateManufacturer(testProduct))
            .thenThrow(IllegalStateException("Failed to create manufacturer"))

        // When/Then
        org.junit.jupiter.api.assertThrows<IllegalStateException> {
            productService.saveProduct(testProduct)
        }

        verify(productDomainService).validateProduct(testProduct)
        verify(supplierService).createSupplier(testProduct)
        verify(manufacturerService).findOrCreateManufacturer(testProduct)
        verifyNoInteractions(productMapper, productRepository)
    }
}