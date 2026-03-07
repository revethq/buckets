package com.revethq.buckets.web.api.mapper

import com.revethq.buckets.domain.Bucket
import com.revethq.buckets.web.dto.BucketDTO

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
