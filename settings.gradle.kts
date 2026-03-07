pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "buckets"

include("core")
include("persistence-runtime")
include("web")
include("provider-s3")
include("provider-gcs")
include("provider-azure-blob")
