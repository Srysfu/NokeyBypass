pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://api.xposed.info/repo/") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
    }
}

rootProject.name = "NokeyBypass"
include(":app")
