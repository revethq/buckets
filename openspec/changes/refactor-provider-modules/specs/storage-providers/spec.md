## MODIFIED Requirements

### Requirement: Multi-Provider Storage Support
The system SHALL support multiple cloud storage providers through a unified StorageProviderClient interface. Each provider SHALL be implemented in a dedicated sub-project (`provider-s3`, `provider-gcs`, `provider-azure-blob`). Supported providers: S3, GCS, MinIO. Azure Blob SHALL throw UnsupportedOperationException.

Provider modules SHALL depend only on the `core` module and SHALL be discovered at runtime via CDI. The `StorageClientFactory` SHALL resolve the appropriate provider implementation based on the bucket's `StorageProvider` type.

#### Scenario: S3 provider client
- **WHEN** a bucket has provider S3
- **THEN** the system creates an S3StorageProviderClient from the `provider-s3` module using AWS SDK v2

#### Scenario: MinIO provider client
- **WHEN** a bucket has provider MINIO
- **THEN** the system creates an S3StorageProviderClient from the `provider-s3` module with endpoint override from the bucket configuration

#### Scenario: GCS provider client
- **WHEN** a bucket has provider GCS
- **THEN** the system creates a GcsStorageProviderClient from the `provider-gcs` module using the secretKey as service account JSON

#### Scenario: Azure Blob not implemented
- **WHEN** a bucket has provider AZURE_BLOB
- **THEN** the system throws UnsupportedOperationException (stub in `provider-azure-blob` module)

#### Scenario: Provider module not on classpath
- **WHEN** a bucket references a provider whose module is not included in the deployment
- **THEN** the system throws an error indicating the provider is not available
