plugins {
    java
    id("net.criticalaction.artemis")
}

artemis {
    fluid {
        generator {

        }
    }
}

tasks.create("causeError") {
    doLast {
        logger.error("Piss!")
    }
}