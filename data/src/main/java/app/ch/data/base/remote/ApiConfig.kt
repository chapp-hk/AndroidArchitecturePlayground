package app.ch.data.base.remote

import app.ch.data.BuildConfig

data class ApiConfig(
    val baseUrl: String = BuildConfig.BASE_URL,
    val apiKey: String = BuildConfig.API_KEY
)
