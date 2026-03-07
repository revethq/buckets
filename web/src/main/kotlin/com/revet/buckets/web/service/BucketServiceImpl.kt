package com.revet.buckets.web.service

import com.revet.buckets.domain.Bucket
import com.revet.buckets.domain.StorageProvider
import com.revet.buckets.repository.BucketRepository
import com.revet.buckets.service.BucketService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.UUID

@ApplicationScoped
class BucketServiceImpl
    @Inject
    constructor(
        private val bucketRepository: BucketRepository,
    ) : BucketService {
        override fun getAllBuckets(includeInactive: Boolean): List<Bucket> = bucketRepository.findAll(includeInactive)

        override fun getBucketById(id: Long): Bucket? = bucketRepository.findById(id)

        override fun getBucketByUuid(uuid: UUID): Bucket? = bucketRepository.findByUuid(uuid)

        override fun createBucket(
            name: String,
            provider: StorageProvider,
            bucketName: String,
            accessKey: String,
            secretKey: String,
            endpoint: String?,
            region: String?,
            presignedUrlDurationMinutes: Int,
        ): Bucket {
            require(name.isNotBlank()) { "Bucket name cannot be blank" }
            require(bucketName.isNotBlank()) { "Bucket name (S3/GCS bucket) cannot be blank" }
            require(accessKey.isNotBlank()) { "Access key cannot be blank" }
            require(secretKey.isNotBlank()) { "Secret key cannot be blank" }
            require(presignedUrlDurationMinutes > 0) { "Presigned URL duration must be positive" }

            if (provider == StorageProvider.MINIO) {
                require(!endpoint.isNullOrBlank()) { "Endpoint is required for MinIO provider" }
            }

            val bucket =
                Bucket.create(
                    name = name,
                    provider = provider,
                    bucketName = bucketName,
                    accessKey = accessKey,
                    secretKey = secretKey,
                    endpoint = endpoint,
                    region = region,
                    presignedUrlDurationMinutes = presignedUrlDurationMinutes,
                )

            return bucketRepository.save(bucket)
        }

        override fun updateBucket(
            id: Long,
            name: String?,
            bucketName: String?,
            endpoint: String?,
            region: String?,
            accessKey: String?,
            secretKey: String?,
            presignedUrlDurationMinutes: Int?,
            isActive: Boolean?,
        ): Bucket? {
            val existing = bucketRepository.findById(id) ?: return null

            name?.let { require(it.isNotBlank()) { "Bucket name cannot be blank" } }
            bucketName?.let { require(it.isNotBlank()) { "Bucket name (S3/GCS bucket) cannot be blank" } }
            accessKey?.let { require(it.isNotBlank()) { "Access key cannot be blank" } }
            secretKey?.let { require(it.isNotBlank()) { "Secret key cannot be blank" } }
            presignedUrlDurationMinutes?.let { require(it > 0) { "Presigned URL duration must be positive" } }

            val updated =
                existing.update(
                    name = name,
                    bucketName = bucketName,
                    endpoint = endpoint,
                    region = region,
                    accessKey = accessKey,
                    secretKey = secretKey,
                    presignedUrlDurationMinutes = presignedUrlDurationMinutes,
                    isActive = isActive,
                )

            return bucketRepository.save(updated)
        }

        override fun updateBucketByUuid(
            uuid: UUID,
            name: String?,
            bucketName: String?,
            endpoint: String?,
            region: String?,
            accessKey: String?,
            secretKey: String?,
            presignedUrlDurationMinutes: Int?,
            isActive: Boolean?,
        ): Bucket? {
            val bucket = bucketRepository.findByUuid(uuid) ?: return null
            return updateBucket(
                bucket.id!!,
                name,
                bucketName,
                endpoint,
                region,
                accessKey,
                secretKey,
                presignedUrlDurationMinutes,
                isActive,
            )
        }

        override fun deleteBucket(id: Long): Boolean = bucketRepository.delete(id)

        override fun deleteBucketByUuid(uuid: UUID): Boolean {
            val bucket = bucketRepository.findByUuid(uuid) ?: return false
            return bucketRepository.delete(bucket.id!!)
        }
    }
