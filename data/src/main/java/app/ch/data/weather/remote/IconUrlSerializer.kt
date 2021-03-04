package app.ch.data.weather.remote

import app.ch.data.BuildConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder

@ExperimentalSerializationApi
@Serializer(forClass = String::class)
object IconUrlSerializer : KSerializer<String> {

    override fun deserialize(decoder: Decoder): String {
        return BuildConfig.ICON_URL.replace("{place-holder}", decoder.decodeString())
    }
}
