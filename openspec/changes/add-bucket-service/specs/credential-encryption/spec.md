## ADDED Requirements

### Requirement: Credential Encryption at Rest
The system SHALL encrypt bucket credentials (accessKey, secretKey) before storing them in the database and decrypt them when loading.

#### Scenario: Encrypt on save
- **WHEN** a bucket is persisted to the database
- **THEN** the accessKey and secretKey are encrypted using AES-256-GCM before storage

#### Scenario: Decrypt on load
- **WHEN** a bucket is loaded from the database
- **THEN** the accessKey and secretKey are decrypted from AES-256-GCM before returning the domain model

### Requirement: AES-256-GCM Encryption
The system SHALL use AES/GCM/NoPadding with a 256-bit key (Base64 encoded), 12-byte random IV, and 128-bit authentication tag. The IV SHALL be prepended to the ciphertext and the result Base64 encoded.

#### Scenario: Encryption configuration
- **WHEN** the application starts
- **THEN** the encryption key is read from `revet.encryption.key` config property (env: `REVET_ENCRYPTION_KEY`)

#### Scenario: Key validation
- **WHEN** the encryption key is decoded from Base64
- **THEN** it MUST be exactly 32 bytes (256 bits)
