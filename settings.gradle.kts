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
    }
}

rootProject.name = "DelightMusicPlayer"
include(":app")
include(":Block-Feature")
include(":Block-Core")
include(":Block-Common")
include(":Block-Core:Media")
include(":Block-Core:Core-Media")
include(":Block-Feature:Feature-List")
include(":Block-Feature:Feature-Player")
include(":Block-Common:Common-Log")
include(":Block-Common:Common-Bom")
