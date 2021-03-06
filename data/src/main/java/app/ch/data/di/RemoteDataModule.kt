package app.ch.data.di

import app.ch.data.BuildConfig
import app.ch.data.base.remote.ApiConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @ExperimentalSerializationApi
    @Provides
    internal fun providesConverterFactory(): Converter.Factory {
        return Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    internal fun providesRequestInterceptor(
        apiConfig: ApiConfig
    ): Interceptor {
        return Interceptor { chain ->
            chain.request()
                .url
                .newBuilder()
                .addQueryParameter("appid", apiConfig.apiKey)
                .build()
                .let { chain.proceed(chain.request().newBuilder().url(it).build()) }
        }
    }

    @Provides
    internal fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    internal fun providesOKHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    internal fun providesRetrofit(
        apiConfig: ApiConfig,
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiConfig.baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
class ApiConfigModule {

    @Provides
    internal fun providesApiConfig(): ApiConfig {
        return ApiConfig()
    }
}
