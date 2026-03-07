## 1. Implementation
- [ ] 1.1 Add `jreleaser` version and plugin to `gradle/libs.versions.toml`
- [ ] 1.2 Add `maven-publish` and `jreleaser` plugins to root `build.gradle.kts`
- [ ] 1.3 Add `java-library`, `maven-publish`, javadoc/source jars, and `PublishingExtension` config to subprojects (excluding `web`)
- [ ] 1.4 Add JReleaser configuration block (signing, GitHub release, Sonatype Maven Central with staging repos for each publishable module)
- [ ] 1.5 Verify `./gradlew publish` stages artifacts successfully
