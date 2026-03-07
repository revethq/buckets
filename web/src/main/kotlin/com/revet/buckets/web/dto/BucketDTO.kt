package com.revet.buckets.web.dto

import com.revet.buckets.domain.StorageProvider
import java.util.UUID

data class BucketDTO(
    val id: Long?,
    val uuid: UUID,
    val name: String,
    val provider: StorageProvider,
    val bucketName: String,
    val endpoint: String?,
    val region: String?,
    val presignedUrlDurationMinutes: Int,
    val isActive: Boolean,
)

data class CreateBucketRequest(
    val name: String,
    val provider: StorageProvider,
    val bucketName: String,
    val accessKey: String,
    val secretKey: String,
    val endpoint: String? = null,
    val region: String? = null,
    val presignedUrlDurationMinutes: Int = 15,
)

data class UpdateBucketRequest(
    val name: String? = null,
    val bucketName: String? = null,
    val endpoint: String? = null,
    val region: String? = null,
    val accessKey: String? = null,
    val secretKey: String? = null,
    val presignedUrlDurationMinutes: Int? = null,
    val isActive: Boolean? = null,
)
