# Change: Add Maven Publishing with JReleaser

## Why
The project has no `publish` task. Library modules (core, persistence-runtime, provider-s3, provider-gcs, provider-azure-blob) need to be published to Maven Central so downstream applications can consume them. The sibling `iam` project already uses JReleaser + maven-publish for this purpose.

## What Changes
- Add `jreleaser` plugin (v1.22.0) to version catalog and root build script
- Add `maven-publish` and `java-library` plugins to publishable subprojects
- Configure POM metadata (license, SCM, developer info) for all library modules
- Configure JReleaser for GPG signing, GitHub releases, and Sonatype Maven Central deployment
- Generate javadoc and source JARs for published modules
- `web` module is excluded from publishing (it is the application, not a library)

## Impact
- Affected specs: maven-publishing (new)
- Affected code: `build.gradle.kts`, `gradle/libs.versions.toml`
