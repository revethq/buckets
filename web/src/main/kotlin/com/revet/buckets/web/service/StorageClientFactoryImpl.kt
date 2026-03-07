package com.revet.buckets.web.service

import com.revet.buckets.domain.Bucket
import com.revet.buckets.service.storage.StorageClientFactory
import com.revet.buckets.service.storage.StorageProviderClient
import com.revet.buckets.service.storage.StorageProviderClientFactory
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Instance
import jakarta.inject.Inject

@ApplicationScoped
class StorageClientFactoryImpl
    @Inject
    constructor(
        private val providerFactories: Instance<StorageProviderClientFactory>,
    ) : StorageClientFactory {
        override fun createClient(bucket: Bucket): StorageProviderClient {
            val factory =
                providerFactories.firstOrNull { bucket.provider in it.supportedProviders() }
                    ?: throw UnsupportedOperationException(
                        "No storage provider available for ${bucket.provider}. " +
                            "Ensure the corresponding provider module is on the classpath.",
                    )
            return factory.createClient(bucket)
        }
    }
