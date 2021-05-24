package net.criticalaction.artemis

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldNotBe
import org.gradle.testfixtures.ProjectBuilder


class ArtemisFluidPluginTest : ShouldSpec({
    context("Using the plugin ID") {
        should("Apply the plugin") {
            val project = ProjectBuilder.builder().build()
            project.pluginManager.apply("net.criticalaction.artemis")

            project.plugins.getPlugin(ArtemisPlugin::class.java) shouldNotBe null
        }
    }
})
