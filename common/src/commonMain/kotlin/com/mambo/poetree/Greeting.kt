package com.mambo.poetree

class Greeting {

    private val platform: Platform = getPlatform()

    fun greeting(): String {
        return "Hello, ${platform.name}!"
    }

}