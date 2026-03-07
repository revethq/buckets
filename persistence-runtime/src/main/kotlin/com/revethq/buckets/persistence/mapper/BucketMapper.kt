package com.revethq.buckets.persistence.mapper

import com.revethq.buckets.domain.Bucket
import com.revethq.buckets.persistence.entity.BucketEntity

object BucketMapper {
    fun toDomain(
        entity: BucketEntity,
        decryptedAccessKey: String,
        decryptedSecretKey: String,
    ): Bucket =
        Bucket(
            id = entity.id,
            uuid = entity.uuid,
            name = entity.name,
            provider = entity.provider,
            bucketName = entity.bucketName,
            endpoint = entity.endpoint,
            region = entity.region,
            accessKey = decryptedAccessKey,
            secretKey = decryptedSecretKey,
            presignedUrlDurationMinutes = entity.presignedUrlDurationMinutes,
            isActive = entity.isActive,
            removedAt = entity.removedAt,
        )

    fun toEntity(
        domain: Bucket,
        encryptedAccessKey: String,
        encryptedSecretKey: String,
    ): BucketEntity {
        val entity = BucketEntity()
        if (domain.id != null) {
            entity.id = domain.id
        }
        entity.uuid = domain.uuid
        entity.name = domain.name
        entity.provider = domain.provider
        entity.bucketName = domain.bucketName
        entity.endpoint = domain.endpoint
        entity.region = domain.region
        entity.accessKey = encryptedAccessKey
        entity.secretKey = encryptedSecretKey
        entity.presignedUrlDurationMinutes = domain.presignedUrlDurationMinutes
        entity.isActive = domain.isActive
        entity.removedAt = domain.removedAt
        return entity
    }

    fun updateEntity(
        entity: BucketEntity,
        domain: Bucket,
        encryptedAccessKey: String,
        encryptedSecretKey: String,
    ) {
        entity.name = domain.name
        entity.bucketName = domain.bucketName
        entity.endpoint = domain.endpoint
        entity.region = domain.region
        entity.accessKey = encryptedAccessKey
        entity.secretKey = encryptedSecretKey
        entity.presignedUrlDurationMinutes = domain.presignedUrlDurationMinutes
        entity.isActive = domain.isActive
        entity.removedAt = domain.removedAt
    }
}
