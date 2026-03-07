package com.revet.buckets.domain

import java.time.Instant

data class FileMetadata(
    val size: Long,
    val contentType: String?,
    val lastModified: Instant,
)
