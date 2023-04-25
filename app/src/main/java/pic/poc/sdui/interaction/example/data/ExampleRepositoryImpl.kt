package pic.poc.sdui.interaction.example.data

import pic.poc.sdui.interaction.example.domain.ExampleRepository
import pic.poc.sdui.interaction.sdui.data.toUiScreen
import pic.poc.sdui.interaction.sdui.domain.UiScreen

internal class ExampleRepositoryImpl(private val service: ExampleService) : ExampleRepository {
    override suspend fun get(exampleId: String): UiScreen {
        val example = service.get(exampleId)
        return example.toUiScreen()
    }
}
