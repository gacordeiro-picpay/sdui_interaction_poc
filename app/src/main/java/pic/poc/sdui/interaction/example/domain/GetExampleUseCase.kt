package pic.poc.sdui.interaction.example.domain

import pic.poc.sdui.interaction.sdui.domain.UiScreen

internal class GetExampleUseCase(private val repository: ExampleRepository) {
    suspend operator fun invoke(exampleId: String): UiScreen = repository.get(exampleId)
}