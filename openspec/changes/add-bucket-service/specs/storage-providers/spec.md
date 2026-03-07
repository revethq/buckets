## ADDED Requirements

### Requirement: Multi-Provider Storage Support
The system SHALL support multiple cloud storage providers through a unified StorageProviderClient interface. Supported providers: S3, GCS, MinIO. Azure Blob SHALL throw UnsupportedOperationException.

#### Scenario: S3 provider client
- **WHEN** a bucket has provider S3
- **THEN** the system creates an S3StorageProviderClient using AWS SDK v2

#### Scenario: MinIO provider client
- **WHEN** a bucket has provider MINIO
- **THEN** the system creates an S3StorageProviderClient with endpoint override from the bucket configuration

#### Scenario: GCS provider client
- **WHEN** a bucket has provider GCS
- **THEN** the system creates a GcsStorageProviderClient using the secretKey as service account JSON

#### Scenario: Azure Blob not implemented
- **WHEN** a bucket has provider AZURE_BLOB
- **THEN** the system throws UnsupportedOperationException

### Requirement: Storage Operations
The system SHALL provide storage operations through each provider client: presigned upload URL generation, presigned download URL generation, file existence check, file metadata retrieval, and file deletion.

#### Scenario: Generate presigned upload URL
- **WHEN** generatePresignedUploadUrl is called with a key and optional contentType
- **THEN** the system returns a PresignedUrl with a time-limited PUT URL valid for the bucket's presignedUrlDurationMinutes

#### Scenario: Generate presigned download URL
- **WHEN** generatePresignedDownloadUrl is called with a key
- **THEN** the system returns a PresignedUrl with a time-limited GET URL

#### Scenario: Check file existence
- **WHEN** exists is called with a key
- **THEN** the system returns true if the file exists in the provider, false otherwise

#### Scenario: Get file metadata
- **WHEN** getFileMetadata is called with a key
- **THEN** the system returns FileMetadata with size, contentType, and lastModified

#### Scenario: Delete file
- **WHEN** delete is called with a key
- **THEN** the system deletes the file and returns true on success
