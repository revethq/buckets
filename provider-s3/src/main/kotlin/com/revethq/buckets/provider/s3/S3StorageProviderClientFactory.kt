package com.revethq.buckets.provider.s3

import com.revethq.buckets.domain.Bucket
import com.revethq.buckets.domain.StorageProvider
import com.revethq.buckets.service.storage.StorageProviderClient
import com.revethq.buckets.service.storage.StorageProviderClientFactory
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class S3StorageProviderClientFactory : StorageProviderClientFactory {
    override fun supportedProviders(): Set<StorageProvider> = setOf(StorageProvider.S3, StorageProvider.MINIO)

    override fun createClient(bucket: Bucket): StorageProviderClient = S3StorageProviderClient(bucket)
}
