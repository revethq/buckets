package com.revet.buckets.repository

import com.revet.buckets.domain.Bucket
import java.util.UUID

interface BucketRepository {
    fun findAll(includeInactive: Boolean = false): List<Bucket>

    fun findById(id: Long): Bucket?

    fun findByUuid(uuid: UUID): Bucket?

    fun save(bucket: Bucket): Bucket

    fun delete(id: Long): Boolean
}
