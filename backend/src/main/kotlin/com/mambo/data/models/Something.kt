package com.mambo.data.models

data class Something(
    val artist: String = "Miss.Pen",
    val message: String = "Now that you found me. Proceed!",
    val tip: String = "add a slash at the end",
    val why: List<String> = listOf(
        "Cause you're awesome",
        "You have come too far",
        "I'll look for you, and find you! ;)"
    )
) {
    companion object {
        val default = Something(
            artist = "Mr.Brush",
            message = "Now that you found me. Proceed!",
            tip = "add a slash at the end",
            why = listOf(
                "Cause you're awesome",
                "You have come too far",
                "I'll look for you, and find you! ;)"
            )
        )
        val alternate = Something(
            artist = "Mr.Paper",
            message = "You're really quite something ain't you",
            tip = "no more tips for you",
            why = listOf(
                "you already found me",
                "I guess you used that tip",
                "How far can you really go? without tips ;)"
            )
        )
    }
}