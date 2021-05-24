package net.criticalaction.artemis.fluidgradle

import com.artemis.FluidGeneratorPreferences
import net.criticalaction.artemis.DEFAULT_EXCLUDE_FROM_CLASSPATH
import net.criticalaction.artemis.DEFAULT_EXCLUDE_GENERATION
import net.criticalaction.artemis.DEFAULT_GENERATE_BOOLEAN_ACCESSORS
import net.criticalaction.artemis.DEFAULT_GENERATE_GROUP_METHODS
import net.criticalaction.artemis.DEFAULT_GENERATE_TAG_METHODS
import net.criticalaction.artemis.DEFAULT_PREFIX_COMPONENT_CREATE
import net.criticalaction.artemis.DEFAULT_PREFIX_COMPONENT_GETTER
import net.criticalaction.artemis.DEFAULT_PREFIX_COMPONENT_HAS
import net.criticalaction.artemis.DEFAULT_PREFIX_COMPONENT_REMOVE
import net.criticalaction.artemis.DEFAULT_SWALLOW_GETTERS
import net.criticalaction.artemis.util.DirectoryPropertyDelegate
import net.criticalaction.artemis.util.ListPropertyDelegate
import net.criticalaction.artemis.util.PropertyDelegate
import org.gradle.api.Action
import org.gradle.api.file.ProjectLayout
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.newInstance
import javax.inject.Inject

open class ArtemisFluidExtension @Inject constructor(
    objectFactory: ObjectFactory,
    projectLayout: ProjectLayout,
) {
    @get:Input
    @get:Optional
    var enabled by PropertyDelegate(objectFactory, Boolean::class, true)

    @get:InputDirectory
    var classpath by DirectoryPropertyDelegate(objectFactory, dir())

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

    open class FluidGeneratorConfig @Inject constructor(
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
        var generateBooleanComponentAccessors by PropertyDelegate(
            objectFactory,
            Boolean::class,
            DEFAULT_GENERATE_BOOLEAN_ACCESSORS
        )

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
