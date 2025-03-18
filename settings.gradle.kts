@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://repo.incode.com/artifactory/libs-incode-welcome")
            credentials {
                username = "upayments"
                password = "kFx1gbAmYKch7vEs"
            }
        }
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "StarPayApp"
include(":app")
