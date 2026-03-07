package com.revet.buckets.domain

data class PresignedUrl(
    val url: String,
    val expiresInMinutes: Int,
    val key: String,
)
