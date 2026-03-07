package com.revethq.buckets.service

import com.revethq.buckets.domain.Bucket
import com.revethq.buckets.domain.StorageProvider
import java.util.UUID

interface BucketService {
    fun getAllBuckets(includeInactive: Boolean = false): List<Bucket>

    fun getBucketById(id: Long): Bucket?

    fun getBucketByUuid(uuid: UUID): Bucket?

    fun createBucket(
        name: String,
        provider: StorageProvider,
        bucketName: String,
        accessKey: String,
        secretKey: String,
        endpoint: String? = null,
        region: String? = null,
        presignedUrlDurationMinutes: Int = 15,
    ): Bucket

    fun updateBucket(
        id: Long,
        name: String? = null,
        bucketName: String? = null,
        endpoint: String? = null,
        region: String? = null,
        accessKey: String? = null,
        secretKey: String? = null,
        presignedUrlDurationMinutes: Int? = null,
        isActive: Boolean? = null,
    ): Bucket?

    fun updateBucketByUuid(
        uuid: UUID,
        name: String? = null,
        bucketName: String? = null,
        endpoint: String? = null,
        region: String? = null,
        accessKey: String? = null,
        secretKey: String? = null,
        presignedUrlDurationMinutes: Int? = null,
        isActive: Boolean? = null,
    ): Bucket?

    fun deleteBucket(id: Long): Boolean

    fun deleteBucketByUuid(uuid: UUID): Boolean
}
