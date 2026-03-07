## 1. Project Scaffold
- [x] 1.1 Create Gradle multi-module project (root, core, web, persistence-runtime, provider-s3, provider-gcs, provider-azure-blob)
- [x] 1.2 Configure `gradle/libs.versions.toml` version catalog (including AWS SDK v2, Google Cloud Storage dependencies)
- [x] 1.3 Configure `settings.gradle.kts` (all 6 modules) and `gradle.properties`
- [x] 1.4 Configure root `build.gradle.kts` with common subproject config
- [x] 1.5 Configure module-specific `build.gradle.kts` files (core, web, persistence-runtime, provider-s3, provider-gcs, provider-azure-blob)
- [x] 1.6 Verify project compiles with `./gradlew build`

## 2. Core Module — Domain Models
- [x] 2.1 Create `StorageProvider` enum (S3, GCS, AZURE_BLOB, MINIO)
- [x] 2.2 Create `Bucket` domain data class with factory methods (create, update, deactivate)
- [x] 2.3 Create `PresignedUrl` data class
- [x] 2.4 Create `FileMetadata` data class
- [x] 2.5 Define repository interface (`BucketRepository`)
- [x] 2.6 Define service interfaces (`BucketService`, `StorageService`, `EncryptionService`)
- [x] 2.7 Define `StorageProviderClient` interface, `StorageProviderClientFactory` interface, and `StorageClientFactory` interface

## 3. Core Module — Permissions
- [x] 3.1 Create `Actions` object with `Bucket` actions (`buckets:ListBuckets`, etc.)
- [x] 3.2 Create `BucketsUrn` helper for URN generation (`urn:revet:buckets:{tenantId}:bucket/{id}`)

## 4. Persistence Module
- [x] 4.1 Create `BucketEntity` with JPA annotations (table: `revet_buckets`)
- [x] 4.2 Create `BucketMapper` (entity ↔ domain, handles encrypted credentials)
- [x] 4.3 Implement `BucketRepositoryImpl` with encryption/decryption on save/load
- [x] 4.4 Implement `AesEncryptionService` (AES-256-GCM, config: `revet.encryption.key`)

## 5. Provider Modules — Storage Providers
- [x] 5.1 Implement `S3StorageProviderClient` in `provider-s3` module (AWS SDK v2, supports S3 + MinIO)
- [x] 5.2 Implement `GcsStorageProviderClient` in `provider-gcs` module (Google Cloud Storage)
- [x] 5.3 Implement `AzureBlobStorageProviderClientFactory` stub in `provider-azure-blob` module (throws UnsupportedOperationException)
- [x] 5.4 Implement CDI-based `StorageClientFactoryImpl` in `web` module (discovers providers via `Instance<StorageProviderClientFactory>`)
- [x] 5.5 Implement `StorageServiceImpl` in `web` module

## 6. Web Module — DTOs & Mappers
- [x] 6.1 Create `BucketDTO`, `CreateBucketRequest`, `UpdateBucketRequest`
- [x] 6.2 Create `BucketDTOMapper` (domain → DTO, excludes credentials)

## 7. Web Module — REST API
- [x] 7.1 Implement `BucketResource` with 5 endpoints (LIST, GET, CREATE, UPDATE, DELETE)
- [x] 7.2 Apply `@RequiresPermission` with `buckets:` actions and correct URNs
- [x] 7.3 Add OpenAPI annotations
- [x] 7.4 Configure `application.properties` (port, DB, encryption key, JWT, storage dev services)

## 8. Validation
- [x] 8.1 Verify `./gradlew build` succeeds
- [x] 8.2 Verify `./gradlew ktlintCheck` passes
