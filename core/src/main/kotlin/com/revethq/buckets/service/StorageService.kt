package com.revethq.buckets.service

import com.revethq.buckets.domain.FileMetadata
import com.revethq.buckets.domain.PresignedUrl

interface StorageService {
    fun generatePresignedUploadUrl(
        bucketId: Long,
        key: String,
        contentType: String?,
    ): PresignedUrl

    fun generatePresignedDownloadUrl(
        bucketId: Long,
        key: String,
    ): PresignedUrl

    fun checkFileExists(
        bucketId: Long,
        key: String,
    ): Boolean

    fun getFileMetadata(
        bucketId: Long,
        key: String,
    ): FileMetadata

    fun deleteFile(
        bucketId: Long,
        key: String,
    ): Boolean
}
