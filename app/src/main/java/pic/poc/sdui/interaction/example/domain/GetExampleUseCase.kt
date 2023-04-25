package pic.poc.sdui.interaction.example.domain

import pic.poc.sdui.interaction.sdui.domain.UiComponent

internal class GetExampleUseCase(private val repository: ExampleRepository) {
    suspend operator fun invoke(exampleId: String): List<UiComponent> = repository.get(exampleId)
}