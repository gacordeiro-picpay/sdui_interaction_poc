package pic.poc.sdui.interaction.example.data

import pic.poc.sdui.interaction.example.domain.ExampleRepository
import pic.poc.sdui.interaction.sdui.data.toComponents
import pic.poc.sdui.interaction.sdui.domain.UiComponent

internal class ExampleRepositoryImpl(private val service: ExampleService): ExampleRepository {
    override suspend fun get(exampleId: String): List<UiComponent> {
        val example = service.get(exampleId)
        return example.toComponents()
    }
}
