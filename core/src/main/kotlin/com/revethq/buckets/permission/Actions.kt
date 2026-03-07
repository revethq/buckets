package com.revethq.buckets.permission

object Actions {
    const val SERVICE = "buckets"

    object Bucket {
        const val LIST = "$SERVICE:ListBuckets"
        const val GET = "$SERVICE:GetBucket"
        const val CREATE = "$SERVICE:CreateBucket"
        const val UPDATE = "$SERVICE:UpdateBucket"
        const val DELETE = "$SERVICE:DeleteBucket"

        val ALL = listOf(LIST, GET, CREATE, UPDATE, DELETE)
        val READ_ONLY = listOf(LIST, GET)
        val WRITE = listOf(CREATE, UPDATE)
    }

    const val ALL_ACTIONS = "$SERVICE:*"
    const val GLOBAL_WILDCARD = "*"
}
