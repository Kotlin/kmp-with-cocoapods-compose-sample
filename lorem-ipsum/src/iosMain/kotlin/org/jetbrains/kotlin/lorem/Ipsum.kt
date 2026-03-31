package org.jetbrains.kotlin.lorem

import swiftPMImport.org.jetbrains.kotlin.lorem.ipsum.lorem.ipsum.LoremIpsum

class Ipsum {

    fun name(): String {
        return LoremIpsum.name().orEmpty()
    }

    fun sentence(): String {
        return LoremIpsum.sentence().orEmpty()
    }

    fun word(): String {
        return LoremIpsum.word().orEmpty()
    }
}