package com.starter.product_information.domain.services


import com.starter.product_information.domain.model.contactInformation.LocalContactInfo
import com.starter.product_information.domain.model.manufacturer.OEMManufacturer
import com.starter.product_information.domain.model.product.DistributionChannel
import com.starter.product_information.domain.model.product.Product
import com.starter.product_information.domain.model.supplier.LocalSupplier
import com.starter.product_information.domain.model.supplier.SupplierClassification
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ProductDomainServiceTest {

    private lateinit var productDomainService: ProductDomainService
    private lateinit var validProduct: Product
    private lateinit var defaultSupplier: LocalSupplier
    private lateinit var defaultManufacturer: OEMManufacturer

    @BeforeEach
    fun setup() {
        productDomainService = ProductDomainService()

        val localContactInfo = LocalContactInfo(
            phoneNumber = "123456789",
            email = "test@supplier.com",
            address = "Test Address 123"
        )

        defaultSupplier = LocalSupplier(
            name = "Test Supplier",
            qualityRating = 4,
            averagePrice = 100.0,
            averageDeliveryTime = 7,
            averageOrderQuantity = 1000,
            isCertified = true,
            supplierClassification = SupplierClassification.Standard,
            contactInfo = localContactInfo
        )

        defaultManufacturer = OEMManufacturer(
            name = "Test Manufacturer",
            country = "USA",
            oemCode = "TEST123"
        )

        validProduct = Product(
            id = 1L,
            name = "Valid Product",
            description = "Valid Description",
            supplier = defaultSupplier,
            manufacturer = defaultManufacturer,
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
    fun `validateProduct should pass for valid product`() {
        productDomainService.validateProduct(validProduct)
        // No exception thrown means test passed
    }

    @Test
    fun `validateProduct should throw exception for blank name`() {
        val invalidProduct = Product(
            id = validProduct.id,
            name = "", // Invalid value
            description = validProduct.description,
            supplier = defaultSupplier,
            manufacturer = defaultManufacturer,
            design = validProduct.design,
            customization = validProduct.customization,
            productionQuantity = validProduct.productionQuantity,
            brand = validProduct.brand,
            distributionChannel = validProduct.distributionChannel,
            price = validProduct.price,
            countryOfOrigin = validProduct.countryOfOrigin,
            certificates = validProduct.certificates,
            deliveryTime = validProduct.deliveryTime,
            companyCountry = validProduct.companyCountry
        )

        val exception = assertThrows<IllegalArgumentException> {
            productDomainService.validateProduct(invalidProduct)
        }
        assertEquals("Product name cannot be blank", exception.message)
    }

    @Test
    fun `validateProduct should throw exception for zero price`() {
        val invalidProduct = Product(
            id = validProduct.id,
            name = validProduct.name,
            description = validProduct.description,
            supplier = defaultSupplier,
            manufacturer = defaultManufacturer,
            design = validProduct.design,
            customization = validProduct.customization,
            productionQuantity = validProduct.productionQuantity,
            brand = validProduct.brand,
            distributionChannel = validProduct.distributionChannel,
            price = 0.0, // Invalid value
            countryOfOrigin = validProduct.countryOfOrigin,
            certificates = validProduct.certificates,
            deliveryTime = validProduct.deliveryTime,
            companyCountry = validProduct.companyCountry
        )

        val exception = assertThrows<IllegalArgumentException> {
            productDomainService.validateProduct(invalidProduct)
        }
        assertEquals("Product price must be greater than zero", exception.message)
    }

    @Test
    fun `validateProduct should throw exception for negative price`() {
        val invalidProduct = Product(
            id = validProduct.id,
            name = validProduct.name,
            description = validProduct.description,
            supplier = defaultSupplier,
            manufacturer = defaultManufacturer,
            design = validProduct.design,
            customization = validProduct.customization,
            productionQuantity = validProduct.productionQuantity,
            brand = validProduct.brand,
            distributionChannel = validProduct.distributionChannel,
            price = -10.0, // Invalid value
            countryOfOrigin = validProduct.countryOfOrigin,
            certificates = validProduct.certificates,
            deliveryTime = validProduct.deliveryTime,
            companyCountry = validProduct.companyCountry
        )

        val exception = assertThrows<IllegalArgumentException> {
            productDomainService.validateProduct(invalidProduct)
        }
        assertEquals("Product price must be greater than zero", exception.message)
    }

    @Test
    fun `validateProduct should throw exception for blank description`() {
        val invalidProduct = Product(
            id = validProduct.id,
            name = validProduct.name,
            description = "", // Invalid value
            supplier = defaultSupplier,
            manufacturer = defaultManufacturer,
            design = validProduct.design,
            customization = validProduct.customization,
            productionQuantity = validProduct.productionQuantity,
            brand = validProduct.brand,
            distributionChannel = validProduct.distributionChannel,
            price = validProduct.price,
            countryOfOrigin = validProduct.countryOfOrigin,
            certificates = validProduct.certificates,
            deliveryTime = validProduct.deliveryTime,
            companyCountry = validProduct.companyCountry
        )

        val exception = assertThrows<IllegalArgumentException> {
            productDomainService.validateProduct(invalidProduct)
        }
        assertEquals("Product description cannot be blank", exception.message)
    }

    @Test
    fun `validateProduct should throw exception for zero production quantity`() {
        val invalidProduct = Product(
            id = validProduct.id,
            name = validProduct.name,
            description = validProduct.description,
            supplier = defaultSupplier,
            manufacturer = defaultManufacturer,
            design = validProduct.design,
            customization = validProduct.customization,
            productionQuantity = 0, // Invalid value
            brand = validProduct.brand,
            distributionChannel = validProduct.distributionChannel,
            price = validProduct.price,
            countryOfOrigin = validProduct.countryOfOrigin,
            certificates = validProduct.certificates,
            deliveryTime = validProduct.deliveryTime,
            companyCountry = validProduct.companyCountry
        )

        val exception = assertThrows<IllegalArgumentException> {
            productDomainService.validateProduct(invalidProduct)
        }
        assertEquals("Production quantity must be greater than zero", exception.message)
    }

    @Test
    fun `validateProduct should throw exception for negative production quantity`() {
        val invalidProduct = Product(
            id = validProduct.id,
            name = validProduct.name,
            description = validProduct.description,
            supplier = defaultSupplier,
            manufacturer = defaultManufacturer,
            design = validProduct.design,
            customization = validProduct.customization,
            productionQuantity = -100, // Invalid value
            brand = validProduct.brand,
            distributionChannel = validProduct.distributionChannel,
            price = validProduct.price,
            countryOfOrigin = validProduct.countryOfOrigin,
            certificates = validProduct.certificates,
            deliveryTime = validProduct.deliveryTime,
            companyCountry = validProduct.companyCountry
        )

        val exception = assertThrows<IllegalArgumentException> {
            productDomainService.validateProduct(invalidProduct)
        }
        assertEquals("Production quantity must be greater than zero", exception.message)
    }
}