package pic.poc.sdui.interaction.example.domain

import pic.poc.sdui.interaction.sdui.domain.UiScreen

internal interface ExampleRepository {
    suspend fun get(exampleId: String): UiScreen
}