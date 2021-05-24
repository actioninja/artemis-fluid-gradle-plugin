pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}

rootProject.name = ("artemis-odb-fluid-plugin")

include(":example")
includeBuild("plugin-build")
