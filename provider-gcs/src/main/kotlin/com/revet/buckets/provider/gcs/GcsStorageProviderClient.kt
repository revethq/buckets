package com.revet.buckets.provider.gcs

import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.HttpMethod
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import com.revet.buckets.domain.Bucket
import com.revet.buckets.domain.FileMetadata
import com.revet.buckets.domain.PresignedUrl
import com.revet.buckets.service.storage.StorageProviderClient
import java.io.ByteArrayInputStream
import java.time.Instant
import java.util.concurrent.TimeUnit

class GcsStorageProviderClient(
    private val bucket: Bucket,
) : StorageProviderClient {
    private val credentials: ServiceAccountCredentials by lazy {
        val jsonStream = ByteArrayInputStream(bucket.secretKey.toByteArray(Charsets.UTF_8))
        ServiceAccountCredentials.fromStream(jsonStream)
    }

    private val storage: Storage by lazy {
        val builder =
            StorageOptions
                .newBuilder()
                .setCredentials(credentials)

        if (bucket.accessKey.isNotBlank()) {
            builder.setProjectId(bucket.accessKey)
        }

        builder.build().service
    }

    override fun generatePresignedUploadUrl(
        key: String,
        contentType: String?,
    ): PresignedUrl {
        val blobInfo =
            BlobInfo
                .newBuilder(bucket.bucketName, key)
                .apply {
                    contentType?.let { setContentType(it) }
                }.build()

        val signedUrl =
            storage.signUrl(
                blobInfo,
                bucket.presignedUrlDurationMinutes.toLong(),
                TimeUnit.MINUTES,
                Storage.SignUrlOption.httpMethod(HttpMethod.PUT),
                Storage.SignUrlOption.withContentType(),
            )

        return PresignedUrl(
            url = signedUrl.toString(),
            expiresInMinutes = bucket.presignedUrlDurationMinutes,
            key = key,
        )
    }

    override fun generatePresignedDownloadUrl(key: String): PresignedUrl {
        val blobInfo = BlobInfo.newBuilder(bucket.bucketName, key).build()

        val signedUrl =
            storage.signUrl(
                blobInfo,
                bucket.presignedUrlDurationMinutes.toLong(),
                TimeUnit.MINUTES,
                Storage.SignUrlOption.httpMethod(HttpMethod.GET),
            )

        return PresignedUrl(
            url = signedUrl.toString(),
            expiresInMinutes = bucket.presignedUrlDurationMinutes,
            key = key,
        )
    }

    override fun exists(key: String): Boolean {
        val blobId = BlobId.of(bucket.bucketName, key)
        val blob = storage.get(blobId)
        return blob != null && blob.exists()
    }

    override fun getFileMetadata(key: String): FileMetadata {
        val blobId = BlobId.of(bucket.bucketName, key)
        val blob =
            storage.get(blobId)
                ?: throw IllegalArgumentException("Object not found: $key")

        return FileMetadata(
            size = blob.size,
            contentType = blob.contentType,
            lastModified = Instant.ofEpochMilli(blob.updateTimeOffsetDateTime.toInstant().toEpochMilli()),
        )
    }

    override fun delete(key: String): Boolean {
        val blobId = BlobId.of(bucket.bucketName, key)
        return storage.delete(blobId)
    }
}
