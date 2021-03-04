package app.ch.data.weather.remote

import app.ch.data.BuildConfig
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encoding.Decoder
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@ExperimentalSerializationApi
class IconUrlSerializerTest {

    @MockK
    private lateinit var decoder: Decoder

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun deserialize() {
        every {
            decoder.decodeString()
        } returns "iconId"

        expectThat(IconUrlSerializer.deserialize(decoder))
            .isEqualTo(BuildConfig.ICON_URL.replace("{place-holder}", "iconId"))
    }
}
