package org.tamersarioglu.easywidgets

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform