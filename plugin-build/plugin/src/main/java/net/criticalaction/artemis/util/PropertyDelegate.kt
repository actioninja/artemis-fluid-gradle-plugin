package net.criticalaction.artemis.util

import org.gradle.kotlin.dsl.property

import org.gradle.api.model.ObjectFactory
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


class PropertyDelegate<T, V : Any>(
    objectFactory: ObjectFactory,
    type: KClass<V>,
    default: V? = null
    ) {
    private val property = objectFactory.property(type).convention(
        default
    )

    operator fun getValue(thisRef: T, property: KProperty<*>): V =
        this.property.get()

    operator fun setValue(thisRef: T, property: KProperty<*>, value: V) =
        this.property.set(value)

}