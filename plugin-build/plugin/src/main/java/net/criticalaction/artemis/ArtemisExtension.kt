package net.criticalaction.artemis

import net.criticalaction.artemis.fluidgradle.ArtemisFluidExtension
import net.criticalaction.artemis.weave.ArtemisWeaveExtension
import org.gradle.api.Action
import org.gradle.api.tasks.Nested
import javax.inject.Inject

abstract class ArtemisExtension {
    @get:Nested
    abstract val fluid: ArtemisFluidExtension

    fun fluid(action: Action<ArtemisFluidExtension>) {
        action.execute(fluid)
    }

    @get:Nested
    abstract val weave: ArtemisWeaveExtension

    fun weave(action: Action<ArtemisWeaveExtension>) {
        action.execute(weave)
    }
}