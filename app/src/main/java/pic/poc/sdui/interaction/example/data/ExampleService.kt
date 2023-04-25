package pic.poc.sdui.interaction.example.data

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import pic.poc.sdui.interaction.networking.makeHttpClient
import pic.poc.sdui.interaction.sdui.data.UiScreenPayload
import java.net.URL

const val BASE_URL = "http://demo8122256.mockable.io"

internal class ExampleService(
    private val client: HttpClient = makeHttpClient(),
    private val baseUrl: String = BASE_URL,
) {
    suspend fun get(exampleId: String): UiScreenPayload {
        val response = client.get(url = URL("$baseUrl/$exampleId"))
        val bodyAsText = response.bodyAsText()
        return Json.decodeFromString(string = bodyAsText)
    }
}
