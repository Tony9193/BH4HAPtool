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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BH4HAPtool"
include(":app")
include(":core:toolkit")
include(":feature:shakedraw")
include(":feature:catchcat")
include(":feature:frisbeegroup")
include(":feature:minesweeper")
include(":feature:tetris")
include(":feature:sokoban")
 
