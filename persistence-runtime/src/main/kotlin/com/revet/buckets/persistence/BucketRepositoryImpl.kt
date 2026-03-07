package com.revet.buckets.persistence

import com.revet.buckets.domain.Bucket
import com.revet.buckets.persistence.entity.BucketEntity
import com.revet.buckets.persistence.mapper.BucketMapper
import com.revet.buckets.repository.BucketRepository
import com.revet.buckets.service.EncryptionService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import java.time.LocalDateTime
import java.util.UUID

@ApplicationScoped
class BucketRepositoryImpl
    @Inject
    constructor(
        private val encryptionService: EncryptionService,
    ) : BucketRepository {
        override fun findAll(includeInactive: Boolean): List<Bucket> {
            val entities =
                if (includeInactive) {
                    BucketEntity.listAll()
                } else {
                    BucketEntity.list("isActive", true)
                }
            return entities.map { entityToDomain(it) }
        }

        override fun findById(id: Long): Bucket? = BucketEntity.findById(id)?.let { entityToDomain(it) }

        override fun findByUuid(uuid: UUID): Bucket? = BucketEntity.find("uuid", uuid).firstResult()?.let { entityToDomain(it) }

        @Transactional
        override fun save(bucket: Bucket): Bucket {
            val encryptedAccessKey = encryptionService.encrypt(bucket.accessKey)
            val encryptedSecretKey = encryptionService.encrypt(bucket.secretKey)

            val entity =
                if (bucket.isNew()) {
                    BucketMapper.toEntity(bucket, encryptedAccessKey, encryptedSecretKey).also { it.persist() }
                } else {
                    val existing =
                        BucketEntity.findById(bucket.id!!)
                            ?: throw IllegalArgumentException("Bucket with id ${bucket.id} not found")
                    BucketMapper.updateEntity(existing, bucket, encryptedAccessKey, encryptedSecretKey)
                    existing
                }
            return entityToDomain(entity)
        }

        @Transactional
        override fun delete(id: Long): Boolean {
            val entity = BucketEntity.findById(id) ?: return false
            entity.isActive = false
            entity.removedAt = LocalDateTime.now()
            return true
        }

        private fun entityToDomain(entity: BucketEntity): Bucket {
            val decryptedAccessKey = encryptionService.decrypt(entity.accessKey)
            val decryptedSecretKey = encryptionService.decrypt(entity.secretKey)
            return BucketMapper.toDomain(entity, decryptedAccessKey, decryptedSecretKey)
        }
    }
