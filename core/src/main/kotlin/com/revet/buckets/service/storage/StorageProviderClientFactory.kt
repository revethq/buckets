package com.revet.buckets.service.storage

import com.revet.buckets.domain.Bucket
import com.revet.buckets.domain.StorageProvider

interface StorageProviderClientFactory {
    fun supportedProviders(): Set<StorageProvider>

    fun createClient(bucket: Bucket): StorageProviderClient
}
