package pic.poc.sdui.interaction.sdui.data

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import pic.poc.sdui.interaction.networking.json
import pic.poc.sdui.interaction.sdui.domain.UiButton
import pic.poc.sdui.interaction.sdui.domain.UiComponent
import pic.poc.sdui.interaction.sdui.domain.UiDivider
import pic.poc.sdui.interaction.sdui.domain.UiEdit
import pic.poc.sdui.interaction.sdui.domain.UiEvent
import pic.poc.sdui.interaction.sdui.domain.UiScreen
import pic.poc.sdui.interaction.sdui.domain.UiSwitch
import pic.poc.sdui.interaction.sdui.domain.UiText

fun UiResponse?.toComponents(): List<UiComponent> =
    this?.components
        .orEmpty()
        .map { it.toComponent() }

fun UiComponentPayload.toComponent(): UiComponent = when (name) {
    "UiScreen" -> jsonData.toUiScreen()
    "UiDivider" -> jsonData.toUiDivider()
    "UiText" -> jsonData.toUiText()
    "UiButton" -> jsonData.toUiButton()
    "UiEdit" -> jsonData.toUiEdit()
    "UiSwitch" -> jsonData.toUiSwitch()
    else -> throw UnsupportedComponentException(this)
}

internal fun String?.toUiScreen(): UiScreen = safelyDecodeComponent {
    val payload: UiScreenPayload = json.decodeFromString(this)
    return UiScreen(
        id = payload.id.orEmpty(),
        children = payload.children.orEmpty().map { it.toComponent() },
        screenEvent = payload.screenEvent.toUiEvent(),
    )
}

internal fun String?.toUiDivider(): UiComponent {
    return UiDivider
}

internal fun String?.toUiText(): UiText = safelyDecodeComponent {
    val payload: UiTextPayload = json.decodeFromString(this)
    return UiText(
        type = payload.type.orEmpty(),
        text = payload.text.orEmpty(),
    )
}

internal fun String?.toUiButton(): UiButton = safelyDecodeComponent {
    val payload: UiButtonPayload = json.decodeFromString(this)
    return UiButton(
        id = payload.id.orEmpty(),
        type = payload.type.orEmpty(),
        text = payload.text.orEmpty(),
        action = payload.action.orEmpty(),
        clickEvent = payload.clickEvent.toUiEvent(),
    )
}

internal fun String?.toUiEdit(): UiEdit = safelyDecodeComponent {
    val payload: UiEditPayload = json.decodeFromString(this)
    return UiEdit(
        id = payload.id.orEmpty(),
        type = payload.type.orEmpty(),
        text = payload.text.orEmpty(),
        mask = payload.mask.orEmpty(),
    )
}

internal fun String?.toUiSwitch(): UiSwitch = safelyDecodeComponent {
    val payload: UiSwitchPayload = json.decodeFromString(this)
    return UiSwitch(
        id = payload.id.orEmpty(),
        type = payload.type.orEmpty(),
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

internal fun isValid(payload: UiEventPayload?) =
    payload != null && !payload.eventName.isNullOrBlank()

internal class UnsupportedComponentException(payload: UiComponentPayload) :
    IllegalStateException("Invalid component received: $payload")

internal class InvalidComponentDataException(name: String, jsonData: String?) :
    IllegalStateException("Invalid jsonData for $name received: $jsonData")

private inline fun <reified T : UiComponent> String?.safelyDecodeComponent(decode: String.() -> T): T =
    runCatching {
        check(this != null)
        decode()
    }.getOrElse {
        throw InvalidComponentDataException(T::class.simpleName.orEmpty(), this)
    }
