## 1. Implementation
- [x] 1.1 Add `jreleaser` version and plugin to `gradle/libs.versions.toml`
- [x] 1.2 Add `maven-publish` and `jreleaser` plugins to root `build.gradle.kts`
- [x] 1.3 Add `java-library`, `maven-publish`, javadoc/source jars, and `PublishingExtension` config to all subprojects (including `web`)
- [x] 1.4 Add JReleaser configuration block (signing, GitHub release, Sonatype Maven Central with staging repos for all 6 modules)
- [x] 1.5 Verify `./gradlew publish` stages artifacts successfully
