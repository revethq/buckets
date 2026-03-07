package com.revet.buckets.permission

import java.util.UUID

object BucketsUrn {
    const val NAMESPACE = "revet"
    const val SERVICE = "buckets"

    object ResourceType {
        const val BUCKET = "bucket"
    }

    fun build(
        tenant: String,
        resourceType: String,
        resourceId: String,
    ): String = "urn:$NAMESPACE:$SERVICE:$tenant:$resourceType/$resourceId"

    fun build(
        tenant: String,
        resourceType: String,
        resourceId: UUID,
    ): String = build(tenant, resourceType, resourceId.toString())

    fun wildcard(
        tenant: String,
        resourceType: String,
    ): String = "urn:$NAMESPACE:$SERVICE:$tenant:$resourceType/*"

    fun bucket(
        tenant: String,
        id: UUID,
    ) = build(tenant, ResourceType.BUCKET, id)

    fun bucket(
        tenant: String,
        id: String,
    ) = build(tenant, ResourceType.BUCKET, id)

    fun bucketWildcard(tenant: String) = wildcard(tenant, ResourceType.BUCKET)
}
