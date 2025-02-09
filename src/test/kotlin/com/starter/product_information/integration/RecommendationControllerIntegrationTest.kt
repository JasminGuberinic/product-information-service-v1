package com.starter.product_information.integration

import com.starter.product_information.domain.model.manufacturer.ManufacturerType
import com.starter.product_information.domain.model.product.DistributionChannel
import com.starter.product_information.domain.model.supplier.SupplierClassification
import com.starter.product_information.domain.model.supplier.SupplierType
import com.starter.product_information.domain.model.userInteraction.InteractionType
import com.starter.product_information.infrastructure.entities.LocalContactInfoEntity
import com.starter.product_information.infrastructure.entities.ManufacturerEntity
import com.starter.product_information.infrastructure.entities.ProductEntity
import com.starter.product_information.infrastructure.entities.SupplierEntity
import com.starter.product_information.infrastructure.entities.UserInteractionEntity
import com.starter.product_information.infrastructure.repositories.ProductRepository
import com.starter.product_information.infrastructure.repositories.UserInteractionRepository
import com.starter.product_information.web.controllers.RecommendationController
import com.starter.product_information.web.dto.RecordInteractionDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest
class RecommendationControllerIntegrationTest {

    @Autowired
    lateinit var recommendationController: RecommendationController

    @Autowired
    lateinit var userInteractionRepository: UserInteractionRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    private lateinit var testProduct1: ProductEntity
    private lateinit var testProduct2: ProductEntity

    @BeforeEach
    fun setup() {
        userInteractionRepository.deleteAll()

        // Create test products
        testProduct1 = createTestProduct("Test Product 1")
        testProduct2 = createTestProduct("Test Product 2")
    }

    private fun createTestProduct(name: String): ProductEntity {
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

        return productRepository.save(
            ProductEntity(
                name = name,
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
        )
    }

    @Test
    @Transactional
    fun `should record user interaction and return successful response`() {
        val interactionDto = RecordInteractionDto(
            userId = 1L,
            productId = testProduct1.id!!,
            interactionType = "VIEW",
            rating = null
        )

        val response = recommendationController.recordInteraction(interactionDto)

        assertEquals(HttpStatus.OK, response.statusCode)

        val savedInteraction = userInteractionRepository.findByUserIdAndProductId(1L, testProduct1.id!!).firstOrNull()
        assertNotNull(savedInteraction)
        assertEquals(InteractionType.VIEW, savedInteraction?.interactionType)
    }

    @Test
    @Transactional
    fun `should return recommendations based on user interactions`() {
        // Create interactions for user 1
        val user1Interaction = UserInteractionEntity(
            userId = 1L,
            productId = testProduct1.id!!,
            interactionType = InteractionType.VIEW
        )
        userInteractionRepository.save(user1Interaction)

        // Create interactions for user 2 (similar taste)
        val user2Interaction1 = UserInteractionEntity(
            userId = 2L,
            productId = testProduct1.id!!,
            interactionType = InteractionType.VIEW
        )
        val user2Interaction2 = UserInteractionEntity(
            userId = 2L,
            productId = testProduct2.id!!,
            interactionType = InteractionType.LIKE
        )
        userInteractionRepository.saveAll(listOf(user2Interaction1, user2Interaction2))

        // Get recommendations for user 1
        val response = recommendationController.getRecommendations(1L, 5)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)

        val recommendations = response.body!!.productIds
        assertTrue(recommendations.contains(testProduct2.id))
    }

    @Test
    @Transactional
    fun `should return most popular products for new user`() {
        // Create multiple interactions for product 2 to make it popular
        val popularProductInteractions = listOf(
            UserInteractionEntity(userId = 2L, productId = testProduct2.id!!, interactionType = InteractionType.VIEW),
            UserInteractionEntity(userId = 3L, productId = testProduct2.id!!, interactionType = InteractionType.LIKE),
            UserInteractionEntity(userId = 4L, productId = testProduct2.id!!, interactionType = InteractionType.PURCHASE)
        )
        userInteractionRepository.saveAll(popularProductInteractions)

        // Get recommendations for new user
        val response = recommendationController.getRecommendations(1L, 5)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)

        val recommendations = response.body!!.productIds
        assertTrue(recommendations.contains(testProduct2.id))
    }
}