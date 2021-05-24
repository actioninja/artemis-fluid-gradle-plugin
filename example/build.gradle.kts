plugins {
    java
}

tasks.create("causeError") {
    doLast {
        logger.error("Piss!")
    }
}
