package net.criticalaction.artemis

import com.artemis.FluidGeneratorPreferences
import net.criticalaction.artemis.fluidgradle.ArtemisFluidTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.withConvention

class ArtemisPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create<ArtemisExtension>(
            "artemis"
        ).apply {
            fluid.generatedSourcesDirectory.convention(
                target.layout.buildDirectory.dir("generated/fluid")
            )
        }

        val extension2 = target.extensions.getByName("artemis")

        target.tasks.create<ArtemisFluidTask>("generateFluidSource") {
            val fluidExtension = extension.fluid
            enabled.set(fluidExtension.enabled)
            classpath.setFrom(fluidExtension.classpath)
            generatedSourcesDirectory.set(fluidExtension.generatedSourcesDirectory)
            preferences.set(extension.fluid.generator.toConfigurationObject())

            preferences.convention(FluidGeneratorPreferences())
        }
    }

}