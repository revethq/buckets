package com.revethq.buckets.domain

data class PresignedUrl(
    val url: String,
    val expiresInMinutes: Int,
    val key: String,
)
