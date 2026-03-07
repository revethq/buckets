package com.revet.buckets.provider.gcs

import com.revet.buckets.domain.Bucket
import com.revet.buckets.domain.StorageProvider
import com.revet.buckets.service.storage.StorageProviderClient
import com.revet.buckets.service.storage.StorageProviderClientFactory
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class GcsStorageProviderClientFactory : StorageProviderClientFactory {
    override fun supportedProviders(): Set<StorageProvider> = setOf(StorageProvider.GCS)

    override fun createClient(bucket: Bucket): StorageProviderClient = GcsStorageProviderClient(bucket)
}
