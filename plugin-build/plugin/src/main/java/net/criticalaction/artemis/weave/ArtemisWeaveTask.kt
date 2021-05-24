package net.criticalaction.artemis.weave

import com.artemis.Weaver
import com.artemis.WeaverLog

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.*
import javax.inject.Inject


open class ArtemisWeaveTask @Inject constructor(
    objectFactory: ObjectFactory
): DefaultTask() {
    /**
     * Root directories for class files.
     */
    @Optional
    @OutputDirectories
    private val classesDirs: FileCollection? = null

    /**
     * Enabled weaving of pooled components (more viable on Android than JVM).
     */
    @get:Input
    private val enablePooledWeaving = false

    /**
     * If false, no weaving will take place (useful for debugging).
     */
    @get:Input
    private val enableArtemisPlugin = false

    @get:Input
    private val optimizeEntitySystems = false

    /**
     * Generate optimized read/write classes for entity link fields, used
     * by the [com.artemis.link.EntityLinkManager].
     */
    @get:Input
    private val generateLinkMutators = false


    @TaskAction
    fun weave() {
        logger.info("Artemis plugin started.")

        if (!enableArtemisPlugin) {
            logger.info("Plugin disabled via 'enableArtemisPlugin' set to false.")
            return
        }

        val start = System.currentTimeMillis()
        //@todo provide gradle alternative.
        //if (context != null && !context.hasDelta(sourceDirectory)) return;

        //@todo provide gradle alternative.
        //if (context != null && !context.hasDelta(sourceDirectory)) return;

//		log.info("");

//		log.info("");
        logger.info("CONFIGURATION")
        logger.info(WeaverLog.LINE.replace("\n", ""))
        logger.info(WeaverLog.format("enablePooledWeaving", enablePooledWeaving))
        logger.info(WeaverLog.format("generateLinkMutators", generateLinkMutators))
        logger.info(WeaverLog.format("optimizeEntitySystems", optimizeEntitySystems))
        logger.info(WeaverLog.format("outputDirectories", classesDirs?.files))
        logger.info(WeaverLog.LINE.replace("\n", ""))

        Weaver.enablePooledWeaving(enablePooledWeaving)
        Weaver.generateLinkMutators(generateLinkMutators)
        Weaver.optimizeEntitySystems(optimizeEntitySystems)

        val weaver = Weaver(classesDirs?.files)

        val processed: WeaverLog = weaver.execute()
        for (s in processed.formattedLog.split("\n")) {
            logger.info(s)
        }
    }
}