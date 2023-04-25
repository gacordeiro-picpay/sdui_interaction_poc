package pic.poc.sdui.interaction.sdui.domain

sealed interface UiEvent {
    data class UserEvent(
        val eventName: String,
        val eventProperties: Map<String, String>,
    ) : UiEvent

    data class InvalidEvent(
        val message: String,
    ) : UiEvent
}

class UiTracker {
    fun track(event: UiEvent) {
        // NoOp for now, but here we would use the proper tools to log each event
    }
}