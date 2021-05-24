package net.criticalaction.artemis.weave

import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectories
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

open class ArtemisWeaveExtension @Inject constructor(
    objectFactory: ObjectFactory
) {
    /**
     * Root directories for class files.
     */
    @get:Optional
    @get:OutputDirectories
    val classesDirs = objectFactory.fileCollection()

    /**
     * Enabled weaving of pooled components (more viable on Android than JVM).
     */
    @get:Input
    @get:Optional
    val enablePooledWeaving = objectFactory.property(Boolean::class).convention(false)

    /**
     * If false, no weaving will take place (useful for debugging).
     */
    @get:Input
    @get:Optional
    val enabled = objectFactory.property(Boolean::class).convention(true)

    @get:Input
    @get:Optional
    val optimizeEntitySystems = objectFactory.property(Boolean::class).convention(true)

    /**
     * Generate optimized read/write classes for entity link fields, used
     * by the [com.artemis.link.EntityLinkManager].
     */
    @get:Input
    @get:Optional
    val generateLinkMutators = objectFactory.property(Boolean::class).convention(true)
}
