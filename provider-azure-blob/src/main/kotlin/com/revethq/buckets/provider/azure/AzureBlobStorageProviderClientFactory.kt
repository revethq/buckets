package com.revethq.buckets.provider.azure

import com.revethq.buckets.domain.Bucket
import com.revethq.buckets.domain.StorageProvider
import com.revethq.buckets.service.storage.StorageProviderClient
import com.revethq.buckets.service.storage.StorageProviderClientFactory
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class AzureBlobStorageProviderClientFactory : StorageProviderClientFactory {
    override fun supportedProviders(): Set<StorageProvider> = setOf(StorageProvider.AZURE_BLOB)

    override fun createClient(bucket: Bucket): StorageProviderClient =
        throw UnsupportedOperationException("Azure Blob provider not yet implemented")
}
