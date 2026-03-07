package com.revet.buckets.web.api.mapper

import com.revet.buckets.domain.Bucket
import com.revet.buckets.web.dto.BucketDTO

object BucketDTOMapper {
    fun toDTO(domain: Bucket): BucketDTO =
        BucketDTO(
            id = domain.id,
            uuid = domain.uuid,
            name = domain.name,
            provider = domain.provider,
            bucketName = domain.bucketName,
            endpoint = domain.endpoint,
            region = domain.region,
            presignedUrlDurationMinutes = domain.presignedUrlDurationMinutes,
            isActive = domain.isActive,
        )
}
