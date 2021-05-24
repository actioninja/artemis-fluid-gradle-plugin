package net.criticalaction.artemis.fluidgradle

import com.artemis.FluidGeneratorPreferences
import net.criticalaction.artemis.*
import net.criticalaction.artemis.util.DirectoryPropertyDelegate
import net.criticalaction.artemis.util.ListPropertyDelegate
import net.criticalaction.artemis.util.PropertyDelegate
import org.gradle.api.Action
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
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
    projectLayout: ProjectLayout,
) {
    @get:Input
    @get:Optional
    var enabled by PropertyDelegate(objectFactory, Boolean::class, true)

    @get:InputDirectory
    var classpath by DirectoryPropertyDelegate(objectFactory)

    @get:OutputDirectory
    @get:Optional
    var generatedSourcesDirectory by DirectoryPropertyDelegate(
        objectFactory,
        projectLayout.buildDirectory.dir("generated/fluid").get()
    )

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
        var swallowGettersWithParameters by PropertyDelegate(objectFactory, Boolean::class, DEFAULT_SWALLOW_GETTERS)

        @get:Input
        @get:Optional
        var prefixComponentGetter by PropertyDelegate(objectFactory, String::class, DEFAULT_PREFIX_COMPONENT_GETTER)

        @get:Input
        @get:Optional
        var prefixComponentCreate by PropertyDelegate(objectFactory, String::class, DEFAULT_PREFIX_COMPONENT_CREATE)

        @get:Input
        @get:Optional
        var prefixComponentHas by PropertyDelegate(objectFactory, String::class, DEFAULT_PREFIX_COMPONENT_HAS)

        @get:Input
        @get:Optional
        var prefixComponentRemove by PropertyDelegate(objectFactory, String::class, DEFAULT_PREFIX_COMPONENT_REMOVE)

        @get:Input
        @get:Optional
        var generateTagMethods by PropertyDelegate(objectFactory, Boolean::class, DEFAULT_GENERATE_TAG_METHODS)

        @get:Input
        @get:Optional
        var generateGroupMethods by PropertyDelegate(objectFactory, Boolean::class, DEFAULT_GENERATE_GROUP_METHODS)

        @get:Input
        @get:Optional
        var generateBooleanComponentAccessors by PropertyDelegate(objectFactory, Boolean::class, DEFAULT_GENERATE_BOOLEAN_ACCESSORS)

        @get:Input
        @get:Optional
        var excludeFromClasspath by ListPropertyDelegate(objectFactory, String::class, DEFAULT_EXCLUDE_FROM_CLASSPATH)

        @get:Input
        @get:Optional
        var excludeFromGeneration by PropertyDelegate(objectFactory, Boolean::class, DEFAULT_EXCLUDE_GENERATION)

        @Internal
        fun toConfigurationObject(): FluidGeneratorPreferences = FluidGeneratorPreferences().apply {
            swallowGettersWithParameters = this@FluidGeneratorConfig.swallowGettersWithParameters
            prefixComponentGetter = this@FluidGeneratorConfig.prefixComponentGetter
            prefixComponentCreate = this@FluidGeneratorConfig.prefixComponentCreate
            prefixComponentHas = this@FluidGeneratorConfig.prefixComponentHas
            prefixComponentRemove = this@FluidGeneratorConfig.prefixComponentRemove
            isGenerateTagMethods = this@FluidGeneratorConfig.generateTagMethods
            isGenerateGroupMethods = this@FluidGeneratorConfig.generateGroupMethods
            isGenerateBooleanComponentAccessors = this@FluidGeneratorConfig.generateBooleanComponentAccessors
            excludeFromClasspath = this@FluidGeneratorConfig.excludeFromClasspath
            isExcludeFromGeneration = this@FluidGeneratorConfig.excludeFromGeneration
        }
    }
}
