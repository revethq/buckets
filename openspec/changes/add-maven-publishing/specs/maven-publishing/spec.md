## ADDED Requirements

### Requirement: Maven Central Publishing
The build system SHALL publish library modules to Maven Central via JReleaser and Sonatype.

Published modules:
- `revet-buckets-core`
- `revet-buckets-persistence-runtime`
- `revet-buckets-provider-s3`
- `revet-buckets-provider-gcs`
- `revet-buckets-provider-azure-blob`

The `web` application module SHALL NOT be published.

#### Scenario: Stage artifacts locally
- **WHEN** `./gradlew publish` is executed
- **THEN** each publishable module produces a Maven publication in its `build/staging-deploy` directory
- **AND** each publication includes the compiled JAR, source JAR, javadoc JAR, and POM

#### Scenario: Application module excluded
- **WHEN** `./gradlew publish` is executed
- **THEN** the `web` module does not produce a Maven publication

### Requirement: Artifact Signing
All published artifacts SHALL be signed with GPG using armored signatures via JReleaser.

#### Scenario: Signed release
- **WHEN** `./gradlew jreleaserFullRelease` is executed
- **THEN** all staged artifacts are signed with GPG before deployment

### Requirement: POM Metadata
Each published module SHALL include POM metadata with project name, description, Apache 2.0 license, SCM URLs, and developer information.

#### Scenario: Valid POM generated
- **WHEN** a module's Maven publication is generated
- **THEN** the POM includes license, SCM, and developer sections
