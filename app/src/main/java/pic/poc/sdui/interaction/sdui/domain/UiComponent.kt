package pic.poc.sdui.interaction.sdui.domain

interface UiComponent

data class UiScreen(
    val id: String,
    val components: List<UiComponent>,
    val data: Map<String, Any>,
    val actions: Map<String, String>,
    val events: Map<String, UiEvent>,
) : UiComponent

object UiDivider : UiComponent {
    override fun toString(): String = "UiDivider"
}

data class UiText(
    val id: String,
    val style: String,
    val text: String,
) : UiComponent

data class UiButton(
    val id: String,
    val style: String,
    val text: String,
) : UiComponent

data class UiEdit(
    val id: String,
    val style: String,
    val text: String,
    val mask: String,
) : UiComponent

data class UiSwitch(
    val id: String,
    val style: String,
    val isChecked: Boolean,
) : UiComponent