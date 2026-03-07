package com.revethq.buckets.service.storage

import com.revethq.buckets.domain.Bucket

interface StorageClientFactory {
    fun createClient(bucket: Bucket): StorageProviderClient
}
