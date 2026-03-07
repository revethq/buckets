# Change: Refactor Storage Providers into Separate Sub-Projects

## Why
The current design places all storage provider implementations (S3, GCS, Azure Blob) in the `web` module. This forces every deployment to include all provider SDKs regardless of which providers are actually used, and couples provider-specific dependencies to the REST layer. Splitting each provider into its own module (`provider-s3`, `provider-gcs`, `provider-azure-blob`) lets consumers include only the providers they need and isolates each provider's SDK dependencies.

## What Changes
- **BREAKING** Module structure changes from 3 modules (`core`, `web`, `persistence-runtime`) to 6 modules (`core`, `web`, `persistence-runtime`, `provider-s3`, `provider-gcs`, `provider-azure-blob`)
- Storage provider implementations move out of `web` into dedicated `provider-*` modules
- `provider-s3` handles both S3 and MinIO (shared AWS SDK v2 client)
- `provider-gcs` handles Google Cloud Storage
- `provider-azure-blob` provides the deferred Azure Blob stub
- `StorageClientFactory` uses CDI to discover available provider implementations at runtime
- Each `provider-*` module depends only on `core` (for the `StorageProviderClient` interface)
- `web` no longer carries any cloud SDK dependencies directly

## Impact
- Affected specs: `storage-providers` (adds module isolation requirement)
- Affected changes: `add-bucket-service` (design.md module structure, tasks.md provider tasks)
