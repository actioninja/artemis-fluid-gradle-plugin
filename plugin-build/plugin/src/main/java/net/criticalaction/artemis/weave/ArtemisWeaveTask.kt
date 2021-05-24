package net.criticalaction.artemis.weave

import com.artemis.Weaver
import com.artemis.WeaverLog
import net.criticalaction.artemis.ARTEMIS_GROUP
import org.gradle.api.DefaultTask
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

open class ArtemisWeaveTask @Inject constructor(
    objectFactory: ObjectFactory
) : DefaultTask() {
    init {
        description = ""
        group = ARTEMIS_GROUP
    }

    /**
     * Root directories for class files.
     */
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
    val enableWeave = objectFactory.property(Boolean::class).convention(true)

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

    @TaskAction
    fun weave() {
        logger.info("Artemis plugin started.")

        if (!enableWeave.get()) {
            logger.info("Plugin disabled via 'enableArtemisPlugin' set to false.")
            return
        }

        logger.info("CONFIGURATION")
        logger.info(WeaverLog.LINE.replace("\n", ""))
        logger.info(WeaverLog.format("enablePooledWeaving", enablePooledWeaving))
        logger.info(WeaverLog.format("generateLinkMutators", generateLinkMutators))
        logger.info(WeaverLog.format("optimizeEntitySystems", optimizeEntitySystems))
        logger.info(WeaverLog.format("outputDirectories", classesDirs.files))
        logger.info(WeaverLog.LINE.replace("\n", ""))

        Weaver.enablePooledWeaving(enablePooledWeaving.get())
        Weaver.generateLinkMutators(generateLinkMutators.get())
        Weaver.optimizeEntitySystems(optimizeEntitySystems.get())

        val weaver = Weaver(classesDirs.files)

        val processed: WeaverLog = weaver.execute()
        for (s in processed.formattedLog.split("\n")) {
            logger.info(s)
        }
    }
}
