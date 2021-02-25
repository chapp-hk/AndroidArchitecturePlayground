package app.ch.data.base.remote

import app.ch.data.BuildConfig
import javax.inject.Inject

class ApiConfig @Inject
constructor() {

    val baseUrl = BuildConfig.BASE_URL

    val apiKey = BuildConfig.API_KEY
}
