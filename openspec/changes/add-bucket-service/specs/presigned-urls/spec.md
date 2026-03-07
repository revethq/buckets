## ADDED Requirements

### Requirement: Presigned URL Generation
The system SHALL generate time-limited presigned URLs for file uploads and downloads by looking up a bucket by ID and delegating to the appropriate storage provider client.

#### Scenario: Generate upload URL by bucket ID
- **WHEN** generatePresignedUploadUrl is called with a bucketId, s3Key, and optional contentType
- **THEN** the system looks up the bucket, creates the provider client, and returns a PresignedUrl

#### Scenario: Generate download URL by bucket ID
- **WHEN** generatePresignedDownloadUrl is called with a bucketId and s3Key
- **THEN** the system looks up the bucket, creates the provider client, and returns a PresignedUrl

#### Scenario: Bucket not found
- **WHEN** a storage operation is requested with a non-existent bucketId
- **THEN** the system throws an appropriate exception

### Requirement: Presigned URL Expiration
Presigned URLs SHALL expire after the bucket's configured presignedUrlDurationMinutes (default 15 minutes).

#### Scenario: URL expiration matches bucket config
- **WHEN** a presigned URL is generated for a bucket with presignedUrlDurationMinutes set to 30
- **THEN** the URL expires after 30 minutes
