package com.revethq.buckets.web

import com.revethq.buckets.permission.Actions
import com.revethq.buckets.permission.BucketsUrn
import com.revethq.iam.permission.discovery.PermissionDeclaration
import com.revethq.iam.permission.discovery.PermissionManifest
import com.revethq.iam.permission.discovery.PermissionProvider
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class BucketsPermissionProvider : PermissionProvider {
    private val bucketResource =
        "urn:${BucketsUrn.NAMESPACE}:${BucketsUrn.SERVICE}" +
            ":{tenantId}:${BucketsUrn.ResourceType.BUCKET}/{bucketId}"

    override fun manifest() =
        PermissionManifest(
            service = Actions.SERVICE,
            permissions =
                listOf(
                    PermissionDeclaration(
                        action = Actions.Bucket.LIST,
                        description = "List all storage bucket configurations",
                        resourceType = bucketResource,
                    ),
                    PermissionDeclaration(
                        action = Actions.Bucket.GET,
                        description = "Retrieve a storage bucket configuration by ID",
                        resourceType = bucketResource,
                    ),
                    PermissionDeclaration(
                        action = Actions.Bucket.CREATE,
                        description = "Create a new storage bucket configuration",
                        resourceType = bucketResource,
                    ),
                    PermissionDeclaration(
                        action = Actions.Bucket.UPDATE,
                        description = "Update an existing storage bucket configuration",
                        resourceType = bucketResource,
                    ),
                    PermissionDeclaration(
                        action = Actions.Bucket.DELETE,
                        description = "Delete a storage bucket configuration",
                        resourceType = bucketResource,
                    ),
                ),
        )
}
