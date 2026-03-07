package com.revethq.buckets.service.storage

import com.revethq.buckets.domain.Bucket
import com.revethq.buckets.domain.StorageProvider

interface StorageProviderClientFactory {
    fun supportedProviders(): Set<StorageProvider>

    fun createClient(bucket: Bucket): StorageProviderClient
}
