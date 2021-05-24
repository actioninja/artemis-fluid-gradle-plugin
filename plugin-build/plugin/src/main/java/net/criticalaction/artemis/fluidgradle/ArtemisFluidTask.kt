package net.criticalaction.artemis.fluidgradle

import com.artemis.FluidGenerator
import com.artemis.FluidGeneratorPreferences
import com.artemis.generator.util.Log
import net.criticalaction.artemis.ARTEMIS_GROUP
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.net.MalformedURLException
import java.net.URL
import javax.inject.Inject


open class ArtemisFluidTask @Inject constructor(
    objectFactory: ObjectFactory
) : DefaultTask() {
    init {
        description = "Generate Fluid API for provided component sources"
        group = ARTEMIS_GROUP
    }


    @get:Input
    val enabled = objectFactory.property(Boolean::class.java).convention(true)

    @get:Input
    val generatedSourcesDirectory = objectFactory.directoryProperty()

    @get:Input
    val classpath: ConfigurableFileCollection = objectFactory.fileCollection()

    @get:Input
    val preferences: Property<FluidGeneratorPreferences> = objectFactory.property(FluidGeneratorPreferences::class.java)

    @TaskAction
    fun fluid() {
        logger.info("Artemis Fluid api plugin started.")
        if (!enabled.get()) {
            logger.info("Artemis Fluid not running because it is disabled.")
            return
        }
        if (classpath.isEmpty) {
            logger.info("Artemis Fluid not running because classpath is not configured.")
            return
        }

        prepareGeneratedSourcesFolder()
        includeGeneratedSourcesInCompilation()
        FluidGenerator().generate(
            classpathAsUrls(preferences.get()),
            generatedSourcesDirectory.files().singleFile,
            createLogAdapter(),
            preferences.get(),
        )
    }

    /**
     * bridge maven/internal logging.
     */
    private fun createLogAdapter(): Log {
        return object : Log {
            override fun info(msg: String) {
                logger.info(msg)
            }

            override fun error(msg: String) {
                logger.error(msg)
            }
        }
    }

    /**
     * Setup generated sources folder if missing.
     */
    private fun prepareGeneratedSourcesFolder() {
        if (!generatedSourcesDirectory.isPresent && !generatedSourcesDirectory.files().singleFile.mkdirs()) {
            logger.error("Could not create $generatedSourcesDirectory")
        }
    }

    /**
     * Must include manually, or maven buids will fail.
     */
    private fun includeGeneratedSourcesInCompilation() {
//		getProject().addCompileSourceRoot(generatedSourcesDirectory().getPath());
    }

    private fun classpathAsUrls(preferences: FluidGeneratorPreferences): Set<URL>? {
        return try {
            val urls: MutableSet<URL> = HashSet()
            for (element in classpath.files) {
                val url: URL = element.toURI().toURL()
                if (!preferences.matchesIgnoredClasspath(url.toString())) {
                    urls.add(url)
                    logger.info("Including: $url")
                }
            }
            urls
        } catch (e: MalformedURLException) {
            throw RuntimeException("failed to complete classpathAsUrls.", e)
        }
    }
}