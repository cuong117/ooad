pluginManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jcenter.bintray.com")
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jcenter.bintray.com")
    }
}

rootProject.name = "thanso"
include(":app")
 