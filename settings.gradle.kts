pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Storygram"
include(":app")
include(":core")
include(":style")
include(":shared")
include(":feature:story")
include(":feature:auth")
include(":feature:splash")
