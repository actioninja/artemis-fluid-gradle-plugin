package net.criticalaction.artemis.fluidgradle

import com.artemis.FluidGeneratorPreferences
import net.criticalaction.artemis.DEFAULT_EXCLUDE_FROM_CLASSPATH
import net.criticalaction.artemis.DEFAULT_EXCLUDE_GENERATION
import net.criticalaction.artemis.DEFAULT_FLUID_ENABLED
import net.criticalaction.artemis.DEFAULT_FLUID_GENERATED_SOURCES_DIRECTORY
import net.criticalaction.artemis.DEFAULT_GENERATE_BOOLEAN_ACCESSORS
import net.criticalaction.artemis.DEFAULT_GENERATE_GROUP_METHODS
import net.criticalaction.artemis.DEFAULT_GENERATE_TAG_METHODS
import net.criticalaction.artemis.DEFAULT_PREFIX_COMPONENT_CREATE
import net.criticalaction.artemis.DEFAULT_PREFIX_COMPONENT_GETTER
import net.criticalaction.artemis.DEFAULT_PREFIX_COMPONENT_HAS
import net.criticalaction.artemis.DEFAULT_PREFIX_COMPONENT_REMOVE
import net.criticalaction.artemis.DEFAULT_SWALLOW_GETTERS
import org.gradle.api.Action
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

open class ArtemisFluidExtension @Inject constructor(
    objectFactory: ObjectFactory,
    projectLayout: ProjectLayout,
) {
    @get:Input
    @get:Optional
    var enabled = objectFactory.property(Boolean::class).convention(DEFAULT_FLUID_ENABLED)

    @get:InputDirectory
    var classpath = objectFactory.directoryProperty()

    @get:OutputDirectory
    @get:Optional
    var generatedSourcesDirectory = objectFactory.directoryProperty().convention(
        projectLayout.buildDirectory.dir(DEFAULT_FLUID_GENERATED_SOURCES_DIRECTORY)
    )

    @get:Nested
    @get:Optional
    val generator: FluidGeneratorConfig = objectFactory.newInstance(FluidGeneratorConfig::class)

    fun generator(action: Action<FluidGeneratorConfig>) {
        action.execute(generator)
    }

    open class FluidGeneratorConfig @Inject constructor(
        objectFactory: ObjectFactory
    ) {
        @get:Input
        @get:Optional
        var swallowGettersWithParameters = objectFactory.property(Boolean::class).convention(DEFAULT_SWALLOW_GETTERS)

        @get:Input
        @get:Optional
        var prefixComponentGetter = objectFactory.property(String::class).convention(DEFAULT_PREFIX_COMPONENT_GETTER)

        @get:Input
        @get:Optional
        var prefixComponentCreate = objectFactory.property(String::class).convention(DEFAULT_PREFIX_COMPONENT_CREATE)

        @get:Input
        @get:Optional
        var prefixComponentHas = objectFactory.property(String::class).convention(DEFAULT_PREFIX_COMPONENT_HAS)

        @get:Input
        @get:Optional
        var prefixComponentRemove = objectFactory.property(String::class).convention(DEFAULT_PREFIX_COMPONENT_REMOVE)

        @get:Input
        @get:Optional
        var generateTagMethods = objectFactory.property(Boolean::class).convention(DEFAULT_GENERATE_TAG_METHODS)

        @get:Input
        @get:Optional
        var generateGroupMethods = objectFactory.property(Boolean::class).convention(DEFAULT_GENERATE_GROUP_METHODS)

        @get:Input
        @get:Optional
        var generateBooleanComponentAccessors = objectFactory.property(Boolean::class).convention(
            DEFAULT_GENERATE_BOOLEAN_ACCESSORS
        )

        @get:Input
        @get:Optional
        var excludeFromClasspath = objectFactory.listProperty(String::class).convention(
            DEFAULT_EXCLUDE_FROM_CLASSPATH
        )

        @get:Input
        @get:Optional
        var excludeFromGeneration = objectFactory.property(Boolean::class).convention(DEFAULT_EXCLUDE_GENERATION)

        @Internal
        fun toConfigurationObject(): FluidGeneratorPreferences = FluidGeneratorPreferences().apply {
            swallowGettersWithParameters = this@FluidGeneratorConfig.swallowGettersWithParameters.get()
            prefixComponentGetter = this@FluidGeneratorConfig.prefixComponentGetter.get()
            prefixComponentCreate = this@FluidGeneratorConfig.prefixComponentCreate.get()
            prefixComponentHas = this@FluidGeneratorConfig.prefixComponentHas.get()
            prefixComponentRemove = this@FluidGeneratorConfig.prefixComponentRemove.get()
            isGenerateTagMethods = this@FluidGeneratorConfig.generateTagMethods.get()
            isGenerateGroupMethods = this@FluidGeneratorConfig.generateGroupMethods.get()
            isGenerateBooleanComponentAccessors = this@FluidGeneratorConfig.generateBooleanComponentAccessors.get()
            excludeFromClasspath = this@FluidGeneratorConfig.excludeFromClasspath.get()
            isExcludeFromGeneration = this@FluidGeneratorConfig.excludeFromGeneration.get()
        }
    }
}
