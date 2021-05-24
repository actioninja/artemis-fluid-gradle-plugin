package net.criticalaction.artemis.util

import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.listProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class ListPropertyDelegate<T, V : Any>(
    objectFactory: ObjectFactory,
    type: KClass<V>,
    default: List<V>? = null
) {
    private val property = objectFactory.listProperty(type).convention(
        default
    )

    operator fun getValue(thisRef: T, property: KProperty<*>): MutableList<V> =
        this.property.get()

    operator fun setValue(thisRef: T, property: KProperty<*>, value: MutableList<V>) =
        this.property.set(value)

}