plugins {
    java
    id("net.criticalaction.artemis")
}

artemis {
    fluid {
        enabled = false
        generator {

        }
    }
}

tasks.create("causeError") {
    doLast {
        logger.error("Piss!")
    }
}