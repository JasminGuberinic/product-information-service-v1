package com.starter.product_information.web.controllers

import com.starter.product_information.application.RequestHandler
import com.starter.product_information.application.commands.create_product.CreateProductCommand
import com.starter.product_information.application.query.get_product.GetProductQuery
import com.starter.product_information.web.dto.ProductDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val requestHandler: RequestHandler
) {

    @PostMapping
    fun createProduct(@RequestBody productDto: ProductDto): ResponseEntity<Any> {
        val command = CreateProductCommand(
            name = productDto.name,
            description = productDto.description,
            price = productDto.price
        )
        requestHandler.handleCommand(command)
        return ResponseEntity.ok("Product created successfully")
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long): ResponseEntity<ProductDto?> {
        val query = GetProductQuery(id)
        val productDto = requestHandler.handleQuery(query) as? ProductDto
        return ResponseEntity.ok(productDto)
    }
}