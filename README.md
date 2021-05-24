# artemis-odb-gradle-plugin

A modernized and more ergonomic version of the official artemis-odb plugins. Configurable via simple declarative syntax
and supports the modern plugin id system. Combines both plugins into one.

# Features

- Support for all features of the weave and fluid API plugins
- Full declarative syntax support

# Versioning

Versions are versioned as `<artemis version>-<semantic plugin version>`

First section will always match an artemis-odb version, second is versions of the plugin itself. Plugin versions follow
semantic versioning, ie <major>.<minor>.<patch>. Major updates are breaking, minor updates include features and
deprecations, patches are fully backwards compatible.

# Usage
## DSL Extension
Plugin provides a DSL to configure the automatically registered tasks.
### Groovy
```groovy
artemis {
   fluid {
      enabled = true
      classpath += ['example_dir']
      generatedSourcesDirectory = projectLayout.buildDirectory.dir("generated/fluid")
      
      generator {
         swallowGettersWithParameters = false
         prefixComponentGetter = "get"
         prefixComponentCreate = ""
         prefixComponentHas = "has"
         prefixComponentRemove ="remove"
         generateTagMethods = true
         generateGroupMethods = true
         generateBooleanComponentAccessors = true
         excludeFromClasspath = ["example_exclusion"]
         excludeFromGeneration = false
      }
   }
   weave {
      enabled = true
      // Root directories for class files
      classesDirs += ['example_dir']
      enablePooledWeaving = false
      optimizeEntitySystems = true
      generateLinkMutators = true
   }
}
```
### Kotlin
```kotlin
artemis {
   fluid {
      enabled.set(true)
      classpath += listOf('example_dir')
      generatedSourcesDirectory.set(projectLayout.buildDirectory.dir("generated/fluid"))

      generator {
         swallowGettersWithParameters.set(false)
         prefixComponentGetter.set("get")
         prefixComponentCreate.set("")
         prefixComponentHas.set("has")
         prefixComponentRemove.set("remove")
         generateTagMethods.set(true)
         generateGroupMethods.set(true)
         generateBooleanComponentAccessors.set(true)
         excludeFromClasspath.set(listOf("example_exclusion"))
         excludeFromGeneration.set(false)
      }
   }
   weave {
      enabled.set(true)
      classesDirs += listOf('example_dir')
      enablePooledWeaving.set(false)
      optimizeEntitySystems.set(true)
      generateLinkMutators.set(true)
   }
}
```


## Tasks


### `weaveBytecode (ArtemisWeaveTask)`
exposes:
 - `classesDirs (ConfigurableFileCollection)`: Input directories for weaving. Bytecode is woven in place.
 - `enableWeave (Property<Boolean>) = true`: Disables weaving entirely when set to false, useful for debugging
 - `enablePooledWeaving (Property<Boolean>) = false`: Enable weaving of pooled components (can worsen performance on JVM,
   helpful on android)
 - `optimizeEntitySystems (Property<Boolean>) = true`: Automatic hotspot optimization for systems that extend
   EntityProcessingSystem
 - `generateLinkMutators (Property<Boolean>) = true`: Generate optmized read/write classes for entity link fields
