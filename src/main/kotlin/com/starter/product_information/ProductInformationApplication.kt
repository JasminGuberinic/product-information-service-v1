package com.starter.product_information

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class ProductInformationApplication

fun main(args: Array<String>) {
	runApplication<ProductInformationApplication>(*args)
}
