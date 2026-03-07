package com.revethq.buckets.web.service

import com.revethq.buckets.domain.FileMetadata
import com.revethq.buckets.domain.PresignedUrl
import com.revethq.buckets.repository.BucketRepository
import com.revethq.buckets.service.StorageService
import com.revethq.buckets.service.storage.StorageClientFactory
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class StorageServiceImpl
    @Inject
    constructor(
        private val bucketRepository: BucketRepository,
        private val storageClientFactory: StorageClientFactory,
    ) : StorageService {
        override fun generatePresignedUploadUrl(
            bucketId: Long,
            key: String,
            contentType: String?,
        ): PresignedUrl {
            val bucket =
                bucketRepository.findById(bucketId)
                    ?: throw IllegalArgumentException("Bucket with id $bucketId not found")

            val client = storageClientFactory.createClient(bucket)
            return client.generatePresignedUploadUrl(key, contentType)
        }

        override fun generatePresignedDownloadUrl(
            bucketId: Long,
            key: String,
        ): PresignedUrl {
            val bucket =
                bucketRepository.findById(bucketId)
                    ?: throw IllegalArgumentException("Bucket with id $bucketId not found")

            val client = storageClientFactory.createClient(bucket)
            return client.generatePresignedDownloadUrl(key)
        }

        override fun checkFileExists(
            bucketId: Long,
            key: String,
        ): Boolean {
            val bucket =
                bucketRepository.findById(bucketId)
                    ?: throw IllegalArgumentException("Bucket with id $bucketId not found")

            val client = storageClientFactory.createClient(bucket)
            return client.exists(key)
        }

        override fun getFileMetadata(
            bucketId: Long,
            key: String,
        ): FileMetadata {
            val bucket =
                bucketRepository.findById(bucketId)
                    ?: throw IllegalArgumentException("Bucket with id $bucketId not found")

            val client = storageClientFactory.createClient(bucket)
            return client.getFileMetadata(key)
        }

        override fun deleteFile(
            bucketId: Long,
            key: String,
        ): Boolean {
            val bucket =
                bucketRepository.findById(bucketId)
                    ?: throw IllegalArgumentException("Bucket with id $bucketId not found")

            val client = storageClientFactory.createClient(bucket)
            return client.delete(key)
        }
    }
