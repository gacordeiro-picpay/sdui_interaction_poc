package pic.poc.sdui.interaction.example.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pic.poc.sdui.interaction.example.data.ExampleRepositoryImpl
import pic.poc.sdui.interaction.example.data.ExampleService
import pic.poc.sdui.interaction.example.domain.GetExampleUseCase

class ExampleViewModel() : ViewModel() {
    private val _state = MutableLiveData<ExampleViewState>()
    val state: LiveData<ExampleViewState> = _state

    private val useCase = GetExampleUseCase(
        repository = ExampleRepositoryImpl(
            service = ExampleService()
        )
    )

    init {
        viewModelScope.launch {
            val example = useCase("1")
            Log.d("GaC Debug", example.toString())
        }
    }
}

data class ExampleViewState(
    val content: String
)