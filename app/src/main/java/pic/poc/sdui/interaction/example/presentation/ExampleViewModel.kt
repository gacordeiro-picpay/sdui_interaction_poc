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
import pic.poc.sdui.interaction.sdui.domain.UiButton
import pic.poc.sdui.interaction.sdui.domain.UiComponent
import pic.poc.sdui.interaction.sdui.domain.UiEdit
import pic.poc.sdui.interaction.sdui.domain.UiSwitch
import pic.poc.sdui.interaction.sdui.domain.UiTracker

class ExampleViewModel : ViewModel() {
    private val tracker = UiTracker()
    private val _state = MutableLiveData<ExampleViewState>()
    val state: LiveData<ExampleViewState> = _state

    private val useCase = GetExampleUseCase(
        repository = ExampleRepositoryImpl(
            service = ExampleService()
        )
    )

    init {
        viewModelScope.launch {
            val screen = useCase("1")
            log("Received: $screen")
            tracker.track(screen.screenEvent)
            _state.value = ExampleViewState(screen.components)
        }
    }

    fun onButtonClicked(component: UiButton) {
        log("onButtonClicked: $component")
        tracker.track(component.clickEvent)
    }

    fun afterTextChanged(component: UiEdit, text: String) {
        log("afterTextChanged: $text")
    }

    fun onSwitchToggled(component: UiSwitch, isChecked: Boolean) {
        log("onSwitchToggled: $isChecked")
    }

    private fun log(message: String) = Log.d("DebugTag", message)
}

data class ExampleViewState(
    val components: List<UiComponent>
)