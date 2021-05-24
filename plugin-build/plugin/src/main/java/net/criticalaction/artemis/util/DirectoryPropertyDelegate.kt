package net.criticalaction.artemis.util

import org.gradle.api.file.Directory
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.property
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class DirectoryPropertyDelegate<T>(
    objectFactory: ObjectFactory,
    default: Directory? = null
) {
    private val property = objectFactory.directoryProperty().convention(
        default
    )

    operator fun getValue(thisRef: T, property: KProperty<*>): Directory =
        this.property.get()

    operator fun setValue(thisRef: T, property: KProperty<*>, value: Directory) =
        this.property.set(value)
}