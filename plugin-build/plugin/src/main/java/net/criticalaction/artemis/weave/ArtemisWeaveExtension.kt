package net.criticalaction.artemis.weave

import net.criticalaction.artemis.util.PropertyDelegate
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectories
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
    val enablePooledWeaving by PropertyDelegate(objectFactory, Boolean::class, false)

    /**
     * If false, no weaving will take place (useful for debugging).
     */
    @get:Input
    val enabled by PropertyDelegate(objectFactory, Boolean::class, false)

    @get:Input
    val optimizeEntitySystems by PropertyDelegate(objectFactory, Boolean::class, false)

    /**
     * Generate optimized read/write classes for entity link fields, used
     * by the [com.artemis.link.EntityLinkManager].
     */
    @get:Input
    val generateLinkMutators by PropertyDelegate(objectFactory, Boolean::class, false)
}
