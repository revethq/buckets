## 1. Update `add-bucket-service` Design
- [x] 1.1 Update `add-bucket-service/design.md` module structure to reflect 6 modules
- [x] 1.2 Update `add-bucket-service/tasks.md` — replace section 1 (scaffold) with 6-module scaffold
- [x] 1.3 Update `add-bucket-service/tasks.md` — replace section 5 (storage providers) with per-module tasks

## 2. Update `add-bucket-service` Scaffold Tasks
- [x] 2.1 Add `provider-s3`, `provider-gcs`, `provider-azure-blob` to `settings.gradle.kts` task
- [x] 2.2 Add module-specific `build.gradle.kts` tasks for each provider module
- [x] 2.3 Add AWS SDK v2 and Google Cloud Storage dependencies to `libs.versions.toml` task

## 3. Update `add-bucket-service` Provider Tasks
- [x] 3.1 Replace "5.1 Implement S3StorageProviderClient" → task under `provider-s3` module
- [x] 3.2 Replace "5.2 Implement GcsStorageProviderClient" → task under `provider-gcs` module
- [x] 3.3 Add "Implement AzureBlobStorageProviderClient stub" task under `provider-azure-blob` module
- [x] 3.4 Replace "5.3 Implement StorageClientFactoryImpl" → CDI-based discovery in `web` module
