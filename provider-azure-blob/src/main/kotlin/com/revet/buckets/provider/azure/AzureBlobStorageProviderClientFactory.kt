package com.revet.buckets.provider.azure

import com.revet.buckets.domain.Bucket
import com.revet.buckets.domain.StorageProvider
import com.revet.buckets.service.storage.StorageProviderClient
import com.revet.buckets.service.storage.StorageProviderClientFactory
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class AzureBlobStorageProviderClientFactory : StorageProviderClientFactory {
    override fun supportedProviders(): Set<StorageProvider> = setOf(StorageProvider.AZURE_BLOB)

    override fun createClient(bucket: Bucket): StorageProviderClient =
        throw UnsupportedOperationException("Azure Blob provider not yet implemented")
}
