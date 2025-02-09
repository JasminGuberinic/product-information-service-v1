package com.starter.product_information.application.scheduler

import com.starter.product_information.infrastructure.entities.ProductEntity
import com.starter.product_information.infrastructure.repositories.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Component
class ProductStatusScheduler(
    private val productRepository: ProductRepository
) {
    private val logger = LoggerFactory.getLogger(ProductStatusScheduler::class.java)

    enum class ItemStatus {
        DRAFT, IN_REVIEW, ACTIVE, DISCONTINUED, ON_HOLD
    }

    @Scheduled(cron = "0 0 */6 * * *") // Runs every 6 hours
    @Transactional
    fun checkAndUpdateStatuses() {
        logger.info("Starting scheduled status check at: ${Instant.now()}")

        try {
            productRepository.findAll().forEach { product ->
                processProductStatus(product)
            }
            logger.info("Completed scheduled status check")
        } catch (e: Exception) {
            logger.error("Error during status check", e)
        }
    }

    private fun processProductStatus(product: ProductEntity) {
        val currentStatusStr = product.status
        val currentStatus = try {
            currentStatusStr?.let { ItemStatus.valueOf(it) }
        } catch (e: IllegalArgumentException) {
            logger.warn("Invalid status found for product ${product.id}: $currentStatusStr")
            null
        }

        val newStatus = determineNewStatus(currentStatus, product.lastStatusUpdate)

        if (newStatus != null && newStatus.name != currentStatusStr) {
            updateProductStatus(product, newStatus)
        }
    }

    private fun determineNewStatus(currentStatus: ItemStatus?, lastUpdate: String?): ItemStatus? {
        if (currentStatus == null || lastUpdate == null) return null

        val daysSinceUpdate = getDaysSinceUpdate(lastUpdate)

        return when {
            currentStatus == ItemStatus.DRAFT && daysSinceUpdate > 7 ->
                checkTransition(currentStatus, ItemStatus.IN_REVIEW)

            currentStatus == ItemStatus.IN_REVIEW && daysSinceUpdate > 3 ->
                checkTransition(currentStatus, ItemStatus.ON_HOLD)

            currentStatus == ItemStatus.ON_HOLD && daysSinceUpdate > 30 ->
                checkTransition(currentStatus, ItemStatus.DISCONTINUED)

            else -> null
        }
    }

    private fun checkTransition(currentStatus: ItemStatus, newStatus: ItemStatus): ItemStatus? {
        return if (canTransitionTo(currentStatus, newStatus)) newStatus else null
    }

    private fun canTransitionTo(currentStatus: ItemStatus, newStatus: ItemStatus): Boolean {
        return when (currentStatus) {
            ItemStatus.DRAFT -> setOf(ItemStatus.IN_REVIEW, ItemStatus.DISCONTINUED)
            ItemStatus.IN_REVIEW -> setOf(ItemStatus.ACTIVE, ItemStatus.DRAFT, ItemStatus.ON_HOLD)
            ItemStatus.ACTIVE -> setOf(ItemStatus.ON_HOLD, ItemStatus.DISCONTINUED)
            ItemStatus.ON_HOLD -> setOf(ItemStatus.ACTIVE, ItemStatus.DISCONTINUED)
            ItemStatus.DISCONTINUED -> emptySet()
        }.contains(newStatus)
    }

    private fun updateProductStatus(product: ProductEntity, newStatus: ItemStatus) {
        product.status = newStatus.name
        product.lastStatusUpdate = Instant.now().epochSecond.toString()
        productRepository.save(product)
        logger.info("Updated product ${product.id} status: ${product.status} -> ${newStatus.name}")
    }

    private fun getDaysSinceUpdate(timestamp: String): Long {
        return try {
            val lastUpdate = Instant.ofEpochSecond(timestamp.toLong())
            val now = Instant.now()
            (now.epochSecond - lastUpdate.epochSecond) / (24 * 60 * 60)
        } catch (e: Exception) {
            logger.error("Error calculating days since update", e)
            0
        }
    }
}