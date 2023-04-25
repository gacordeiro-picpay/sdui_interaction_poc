package pic.poc.sdui.interaction.sdui.data

import kotlinx.serialization.decodeFromString
import pic.poc.sdui.interaction.networking.json
import pic.poc.sdui.interaction.sdui.domain.UiButton
import pic.poc.sdui.interaction.sdui.domain.UiComponent
import pic.poc.sdui.interaction.sdui.domain.UiDivider
import pic.poc.sdui.interaction.sdui.domain.UiEdit
import pic.poc.sdui.interaction.sdui.domain.UiEvent
import pic.poc.sdui.interaction.sdui.domain.UiScreen
import pic.poc.sdui.interaction.sdui.domain.UiSwitch
import pic.poc.sdui.interaction.sdui.domain.UiText

fun UiResponse?.toUiScreen(): UiScreen = takeIf(::isValid)?.run {
    if (data.isNullOrEmpty()) null
    else rootComponent?.toUiScreenWith(data)
} ?: throw UnsupportedComponentException(toString())

internal fun UiComponentPayload.toUiScreenWith(
    data: Map<String, String>,
): UiScreen = safelyDecodeComponent {
    val jsonData = data[id.orEmpty()].orEmpty()
    val payload: UiScreenPayload = json.decodeFromString(jsonData)
    return UiScreen(
        id = id.orEmpty(),
        components = children.orEmpty().map { it.toUiComponentWith(data) },
        screenEvent = payload.viewEvent.toUiEvent(),
    )
}

internal fun UiComponentPayload.toUiComponentWith(data: Map<String, String>): UiComponent {
    val jsonData = data[id.orEmpty()].orEmpty()
    return when (type) {
        "UiText" -> toUiTextWith(jsonData)
        "UiButton" -> toUiButtonWith(jsonData)
        "UiEdit" -> toUiEditWith(jsonData)
        "UiSwitch" -> toUiSwitchWith(jsonData)
        "UiDivider" -> UiDivider
        else -> throw UnsupportedComponentException(toString())
    }
}

internal fun UiComponentPayload.toUiTextWith(jsonData: String): UiText = safelyDecodeComponent {
    val payload: UiTextPayload = json.decodeFromString(jsonData)
    return UiText(
        id = id.orEmpty(),
        style = payload.style.orEmpty(),
        text = payload.text.orEmpty(),
    )
}

internal fun UiComponentPayload.toUiButtonWith(jsonData: String): UiButton = safelyDecodeComponent {
    val payload: UiButtonPayload = json.decodeFromString(jsonData)
    return UiButton(
        id = id.orEmpty(),
        style = payload.style.orEmpty(),
        text = payload.text.orEmpty(),
        action = payload.action.orEmpty(),
        clickEvent = payload.clickEvent.toUiEvent(),
    )
}

internal fun UiComponentPayload.toUiEditWith(jsonData: String): UiEdit = safelyDecodeComponent {
    val payload: UiEditPayload = json.decodeFromString(jsonData)
    return UiEdit(
        id = id.orEmpty(),
        style = payload.style.orEmpty(),
        text = payload.text.orEmpty(),
        mask = payload.mask.orEmpty(),
    )
}

internal fun UiComponentPayload.toUiSwitchWith(jsonData: String): UiSwitch = safelyDecodeComponent {
    val payload: UiSwitchPayload = json.decodeFromString(jsonData)
    return UiSwitch(
        id = id.orEmpty(),
        style = payload.style.orEmpty(),
        isChecked = payload.isChecked ?: false,
    )
}

internal fun UiEventPayload?.toUiEvent(): UiEvent =
    takeIf(::isValid)?.run {
        UiEvent.UserEvent(
            eventName = eventName.orEmpty(),
            eventProperties = eventProperties.orEmpty(),
        )
    } ?: UiEvent.InvalidEvent("Invalid UiEvent received: ${toString()}")

internal fun isValid(payload: UiResponse?) =
    isValid(payload?.rootComponent) && payload?.data.orEmpty().isNotEmpty()

internal fun isValid(payload: UiComponentPayload?) =
    payload != null && !payload.id.isNullOrBlank()

internal fun isValid(payload: UiEventPayload?) =
    payload != null && !payload.eventName.isNullOrBlank()

internal class UnsupportedComponentException(payload: String) :
    IllegalStateException("Invalid component received: $payload")

internal class InvalidComponentDataException(name: String, jsonData: String?) :
    IllegalStateException("Invalid jsonData for $name received: $jsonData")

private inline fun <reified T : UiComponent> UiComponentPayload?.safelyDecodeComponent(
    decode: () -> T
): T = runCatching {
    check(this != null)
    decode()
}.getOrElse {
    throw InvalidComponentDataException(T::class.simpleName.orEmpty(), "$this")
}
