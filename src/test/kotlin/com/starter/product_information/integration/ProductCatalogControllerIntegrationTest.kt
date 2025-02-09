package com.starter.product_information.integration

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductCatalogControllerIntegrationTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun `should upload and download file`() {
        // Create test file
        val testFile = File("test.txt")
        testFile.writeText("Hello, this is a test file!")

        // Prepare upload request
        val headers = HttpHeaders()
        headers.contentType = MediaType.MULTIPART_FORM_DATA

        val body = LinkedMultiValueMap<String, Any>()
        body.add("file", FileSystemResource(testFile))

        val requestEntity = HttpEntity(body, headers)

        // Upload file
        val uploadResponse = restTemplate.postForObject(
            "http://localhost:$port/api/files/upload",
            requestEntity,
            Map::class.java
        )

        // Get filename from response
        val filename = uploadResponse?.get("filename") as String
        assertTrue(filename.isNotEmpty())

        // Download file
        val downloadResponse = restTemplate.getForObject(
            "http://localhost:$port/api/files/download/$filename",
            String::class.java
        )

        // Verify content
        assertEquals("Hello, this is a test file!", downloadResponse)

        // Clean up
        testFile.delete()
    }
}