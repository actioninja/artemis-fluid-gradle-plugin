package net.criticalaction.artemis

const val ARTEMIS_GROUP = "artemis"

// Fluid plugin options
const val DEFAULT_FLUID_ENABLED = true
const val DEFAULT_FLUID_GENERATED_SOURCES_DIRECTORY = "generated/fluid"

// Fluid generator preferences
const val DEFAULT_SWALLOW_GETTERS = false
const val DEFAULT_PREFIX_COMPONENT_GETTER = "get"
const val DEFAULT_PREFIX_COMPONENT_CREATE = ""
const val DEFAULT_PREFIX_COMPONENT_HAS = "has"
const val DEFAULT_PREFIX_COMPONENT_REMOVE = "remove"
const val DEFAULT_GENERATE_TAG_METHODS = true
const val DEFAULT_GENERATE_GROUP_METHODS = true
const val DEFAULT_GENERATE_BOOLEAN_ACCESSORS = true
val DEFAULT_EXCLUDE_FROM_CLASSPATH = listOf<String>()
const val DEFAULT_EXCLUDE_GENERATION = false
