package com.example.emergencykmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform