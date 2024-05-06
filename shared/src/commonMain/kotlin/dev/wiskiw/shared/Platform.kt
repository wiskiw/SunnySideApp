package dev.wiskiw.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform