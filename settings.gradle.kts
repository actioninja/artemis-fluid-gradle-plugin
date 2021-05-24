pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}

rootProject.name = ("artemis-odb-gradle-plugin")

include(":example")
includeBuild("plugin-build")
