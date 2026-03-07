package com.revet.buckets.service.storage

import com.revet.buckets.domain.Bucket

interface StorageClientFactory {
    fun createClient(bucket: Bucket): StorageProviderClient
}
