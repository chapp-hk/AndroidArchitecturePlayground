package app.ch.data.weather.remote

import app.ch.data.BuildConfig
import io.mockk.every
import io.mockk.mockk
import kotlinx.serialization.encoding.Decoder
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class IconUrlSerializerTest {

    @Test
    fun deserialize() {
        val decoder = mockk<Decoder> {
            every { decodeString() } returns "iconId"
        }

        expectThat(IconUrlSerializer.deserialize(decoder))
            .isEqualTo(BuildConfig.ICON_URL.replace("{place-holder}", "iconId"))
    }
}
