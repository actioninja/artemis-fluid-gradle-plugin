package net.criticalaction.artemis.fluidgradle

import com.artemis.FluidGeneratorPreferences
import net.criticalaction.artemis.*
import org.gradle.api.Action
import org.gradle.api.Named
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

abstract class ArtemisFluidExtension @Inject constructor(
    objectFactory: ObjectFactory,
) {
    @get:Input
    @get:Optional
    val enabled: Property<Boolean> = objectFactory.property(Boolean::class).convention(
        true
    )

    @get:InputDirectory
    val classpath: DirectoryProperty = objectFactory.directoryProperty()

    @get:OutputDirectory
    @get:Optional
    val generatedSourcesDirectory: DirectoryProperty = objectFactory.directoryProperty()

    @get:Nested
    @get:Optional
    val generator: FluidGeneratorConfig = objectFactory.newInstance(FluidGeneratorConfig::class)

    fun generator(action: Action<FluidGeneratorConfig>) {
        action.execute(generator)
    }

    abstract class FluidGeneratorConfig @Inject constructor(
        objectFactory: ObjectFactory
    ) {
        @get:Input
        @get:Optional
        val swallowGettersWithParameters: Property<Boolean> = objectFactory.property(Boolean::class).convention(
            DEFAULT_SWALLOW_GETTERS
        )

        @get:Input
        @get:Optional
        val prefixComponentGetter: Property<String> = objectFactory.property(String::class).convention(
            DEFAULT_PREFIX_COMPONENT_GETTER
        )

        @get:Input
        @get:Optional
        val prefixComponentCreate: Property<String> = objectFactory.property(String::class).convention(
            DEFAULT_PREFIX_COMPONENT_CREATE
        )

        @get:Input
        @get:Optional
        val prefixComponentHas: Property<String> = objectFactory.property(String::class).convention(
            DEFAULT_PREFIX_COMPONENT_HAS
        )

        @get:Input
        @get:Optional
        val prefixComponentRemove: Property<String> = objectFactory.property(String::class).convention(
            DEFAULT_PREFIX_COMPONENT_REMOVE
        )

        @get:Input
        @get:Optional
        val generateTagMethods: Property<Boolean> = objectFactory.property(Boolean::class).convention(
            DEFAULT_GENERATE_TAG_METHODS
        )

        @get:Input
        @get:Optional
        val generateGroupMethods: Property<Boolean> = objectFactory.property(Boolean::class).convention(
            DEFAULT_GENERATE_GROUP_METHODS
        )

        @get:Input
        @get:Optional
        val generateBooleanComponentAccessors: Property<Boolean> = objectFactory.property(Boolean::class).convention(
            DEFAULT_GENERATE_BOOLEAN_ACCESSORS
        )

        @get:Input
        @get:Optional
        val excludeFromClasspath: ListProperty<String> = objectFactory.listProperty(String::class).convention(
            DEFAULT_EXCLUDE_FROM_CLASSPATH
        )

        @get:Input
        @get:Optional
        val excludeFromGeneration: Property<Boolean> = objectFactory.property(Boolean::class).convention(
            DEFAULT_EXCLUDE_GENERATION
        )

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
