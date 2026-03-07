package com.revethq.buckets.provider.gcs

import com.revethq.buckets.domain.Bucket
import com.revethq.buckets.domain.StorageProvider
import com.revethq.buckets.service.storage.StorageProviderClient
import com.revethq.buckets.service.storage.StorageProviderClientFactory
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class GcsStorageProviderClientFactory : StorageProviderClientFactory {
    override fun supportedProviders(): Set<StorageProvider> = setOf(StorageProvider.GCS)

    override fun createClient(bucket: Bucket): StorageProviderClient = GcsStorageProviderClient(bucket)
}
