package com.starter.product_information.integration

import com.starter.product_information.infrastructure.entities.Product
import com.starter.product_information.infrastructure.repositories.ProductRepository
import com.starter.product_information.web.controllers.ProductController
import com.starter.product_information.web.dto.ProductDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertNotNull
import kotlin.test.assertEquals

@SpringBootTest
class ProductControllerIntegrationTest {

    @Autowired
    lateinit var productController: ProductController

    @Autowired
    lateinit var productRepository: ProductRepository // Repo za interakciju sa bazom

    @Test
    @Transactional
    fun `should create product and save to database`() {
        val productDto = ProductDto(
            name = "Test Product",
            description = "Test Description",
            price = 99.99
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
        val productDto = ProductDto(
            name = "Test Product",
            description = "Test Description",
            price = 99.99
        )

        val product = Product(
            name = productDto.name,
            description = productDto.description,
            price = productDto.price
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
