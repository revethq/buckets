package com.revet.buckets.provider.s3

import com.revet.buckets.domain.Bucket
import com.revet.buckets.domain.StorageProvider
import com.revet.buckets.service.storage.StorageProviderClient
import com.revet.buckets.service.storage.StorageProviderClientFactory
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class S3StorageProviderClientFactory : StorageProviderClientFactory {
    override fun supportedProviders(): Set<StorageProvider> = setOf(StorageProvider.S3, StorageProvider.MINIO)

    override fun createClient(bucket: Bucket): StorageProviderClient = S3StorageProviderClient(bucket)
}
