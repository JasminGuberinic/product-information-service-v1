package com.starter.product_information.application.service

import com.starter.product_information.domain.model.contactInformation.LocalContactInfo
import com.starter.product_information.domain.model.manufacturer.OEMManufacturer
import com.starter.product_information.domain.model.product.DistributionChannel
import com.starter.product_information.domain.model.product.Product
import com.starter.product_information.domain.model.supplier.LocalSupplier
import com.starter.product_information.domain.model.supplier.SupplierClassification
import com.starter.product_information.domain.model.supplier.SupplierType
import com.starter.product_information.domain.services.SupplierDomainService
import com.starter.product_information.infrastructure.entities.*
import com.starter.product_information.infrastructure.repositories.SupplierRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SupplierServiceTest {

    private lateinit var supplierService: SupplierService

    @Mock
    private lateinit var supplierRepository: SupplierRepository

    @Mock
    private lateinit var supplierDomainService: SupplierDomainService

    private lateinit var testProduct: Product

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        supplierService = SupplierService(supplierRepository, supplierDomainService)

        val localContactInfo = LocalContactInfo(
            phoneNumber = "123456789",
            email = "test@supplier.com",
            address = "Test Address 123"
        )

        val supplier = LocalSupplier(
            name = "Test Supplier",
            qualityRating = 4,
            averagePrice = 100.0,
            averageDeliveryTime = 7,
            averageOrderQuantity = 1000,
            isCertified = true,
            supplierClassification = SupplierClassification.Standard,
            contactInfo = localContactInfo
        )

        testProduct = Product(
            id = 1L,
            name = "Test Product",
            description = "Test Description",
            supplier = supplier,
            manufacturer = OEMManufacturer(
                name = "Test Manufacturer",
                country = "USA",
                oemCode = "TEST123"
            ),
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
    }

    @Test
    fun `should create supplier`() {
        // Given
        val supplierType = SupplierType.LOCAL
        val supplierEntity = SupplierEntity(
            name = "Test Supplier",
            supplierType = supplierType,
            supplierClassification = SupplierClassification.Standard,
            qualityRating = 4,
            averagePrice = 100.0,
            averageDeliveryTime = 7,
            averageOrderQuantity = 1000,
            isCertified = true
        )

        `when`(supplierDomainService.determineSupplierType(testProduct)).thenReturn(supplierType)
        `when`(supplierRepository.save(any(SupplierEntity::class.java))).thenReturn(supplierEntity)

        // When
        supplierService.createSupplier(testProduct)

        // Then
        verify(supplierDomainService).determineSupplierType(testProduct)
        verify(supplierRepository).save(any(SupplierEntity::class.java))
    }
}