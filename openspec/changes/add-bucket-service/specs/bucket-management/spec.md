## ADDED Requirements

### Requirement: Bucket CRUD Operations
The system SHALL provide create, read, update, and soft-delete operations for bucket storage configurations via REST API at `/api/v1/buckets`.

A bucket SHALL have: uuid, name, provider (S3/GCS/MINIO/AZURE_BLOB), bucketName, endpoint (optional), region (optional), accessKey, secretKey, presignedUrlDurationMinutes (default 15), isActive, and removedAt.

#### Scenario: Create a new bucket
- **WHEN** a POST request is sent to `/api/v1/buckets` with valid name, provider, bucketName, accessKey, and secretKey
- **THEN** the system creates a bucket and returns 201 with a BucketDTO (credentials excluded)

#### Scenario: Create bucket with MinIO requires endpoint
- **WHEN** a POST request is sent with provider MINIO and no endpoint
- **THEN** the system returns 400 Bad Request

#### Scenario: List active buckets
- **WHEN** a GET request is sent to `/api/v1/buckets`
- **THEN** the system returns 200 with a list of active buckets (credentials excluded)

#### Scenario: List all buckets including inactive
- **WHEN** a GET request is sent to `/api/v1/buckets?includeInactive=true`
- **THEN** the system returns 200 with all buckets including soft-deleted ones

#### Scenario: Get bucket by UUID
- **WHEN** a GET request is sent to `/api/v1/buckets/{uuid}` with a valid UUID
- **THEN** the system returns 200 with the bucket (credentials excluded)

#### Scenario: Get non-existent bucket
- **WHEN** a GET request is sent to `/api/v1/buckets/{uuid}` with an unknown UUID
- **THEN** the system returns 404 Not Found

#### Scenario: Update bucket
- **WHEN** a PUT request is sent to `/api/v1/buckets/{uuid}` with partial fields
- **THEN** the system updates only the provided fields and returns 200

#### Scenario: Soft-delete bucket
- **WHEN** a DELETE request is sent to `/api/v1/buckets/{uuid}`
- **THEN** the system sets isActive to false and removedAt to the current timestamp, returning 204

### Requirement: Bucket Validation
The system SHALL enforce validation rules when creating or updating buckets.

#### Scenario: Reject blank required fields
- **WHEN** a create or update request provides a blank name, bucketName, accessKey, or secretKey
- **THEN** the system returns 400 Bad Request

#### Scenario: Reject non-positive presigned URL duration
- **WHEN** a create or update request provides presignedUrlDurationMinutes <= 0
- **THEN** the system returns 400 Bad Request

### Requirement: Bucket API Credential Exclusion
The system SHALL never include accessKey or secretKey in API responses.

#### Scenario: Credentials excluded from response
- **WHEN** a bucket is returned via any API endpoint
- **THEN** the response body contains no accessKey or secretKey fields
