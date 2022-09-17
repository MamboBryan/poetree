package com.mambo.poetree

class DesktopPlatform : Platform {
    override val name: String
        get() = "Hello Desktop"
}

actual fun getPlatform(): Platform = DesktopPlatform()