package com.revethq.buckets.service.storage

import com.revethq.buckets.domain.FileMetadata
import com.revethq.buckets.domain.PresignedUrl

interface StorageProviderClient {
    fun generatePresignedUploadUrl(
        key: String,
        contentType: String?,
    ): PresignedUrl

    fun generatePresignedDownloadUrl(key: String): PresignedUrl

    fun exists(key: String): Boolean

    fun getFileMetadata(key: String): FileMetadata

    fun delete(key: String): Boolean
}
