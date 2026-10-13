package io.github.youhwanjung.pinly.auth.dto

data class TokenResponse(
    val accessToken : String,
    val refreshToken: String,
)
