package com.revethq.buckets.domain

import java.time.LocalDateTime
import java.util.UUID

data class Bucket(
    val id: Long?,
    val uuid: UUID,
    val name: String,
    val provider: StorageProvider,
    val bucketName: String,
    val endpoint: String?,
    val region: String?,
    val accessKey: String,
    val secretKey: String,
    val presignedUrlDurationMinutes: Int,
    val isActive: Boolean,
    val removedAt: LocalDateTime?,
) {
    companion object {
        fun create(
            name: String,
            provider: StorageProvider,
            bucketName: String,
            accessKey: String,
            secretKey: String,
            endpoint: String? = null,
            region: String? = null,
            presignedUrlDurationMinutes: Int = 15,
        ): Bucket =
            Bucket(
                id = null,
                uuid = UUID.randomUUID(),
                name = name,
                provider = provider,
                bucketName = bucketName,
                endpoint = endpoint,
                region = region,
                accessKey = accessKey,
                secretKey = secretKey,
                presignedUrlDurationMinutes = presignedUrlDurationMinutes,
                isActive = true,
                removedAt = null,
            )
    }

    fun update(
        name: String? = null,
        bucketName: String? = null,
        endpoint: String? = null,
        region: String? = null,
        accessKey: String? = null,
        secretKey: String? = null,
        presignedUrlDurationMinutes: Int? = null,
        isActive: Boolean? = null,
    ): Bucket =
        this.copy(
            name = name ?: this.name,
            bucketName = bucketName ?: this.bucketName,
            endpoint = endpoint ?: this.endpoint,
            region = region ?: this.region,
            accessKey = accessKey ?: this.accessKey,
            secretKey = secretKey ?: this.secretKey,
            presignedUrlDurationMinutes = presignedUrlDurationMinutes ?: this.presignedUrlDurationMinutes,
            isActive = isActive ?: this.isActive,
        )

    fun deactivate(): Bucket =
        this.copy(
            isActive = false,
            removedAt = LocalDateTime.now(),
        )

    fun isNew(): Boolean = id == null
}
