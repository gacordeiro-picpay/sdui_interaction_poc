package pic.poc.sdui.interaction.sdui.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UiResponse(
    @SerialName("root_component") val rootComponent: UiComponentPayload? = null,
    @SerialName("data") val data: Map<String, String>? = null,
)

@Serializable
data class UiComponentPayload(
    @SerialName("id") val id: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("children") val children: List<UiComponentPayload>? = null,
)

@Serializable
data class UiEventPayload(
    @SerialName("event_name") val eventName: String? = null,
    @SerialName("event_properties") val eventProperties: Map<String, String>? = null,
)

@Serializable
data class UiScreenPayload(
    @SerialName("view_event") val viewEvent: UiEventPayload? = null,
)

@Serializable
data class UiTextPayload(
    @SerialName("style") val style: String? = null,
    @SerialName("text") val text: String? = null,
)

@Serializable
data class UiButtonPayload(
    @SerialName("style") val style: String? = null,
    @SerialName("text") val text: String? = null,
    @SerialName("action") val action: String? = null,
    @SerialName("click_event") val clickEvent: UiEventPayload? = null,
)

@Serializable
data class UiEditPayload(
    @SerialName("style") val style: String? = null,
    @SerialName("text") val text: String? = null,
    @SerialName("mask") val mask: String? = null,
)

@Serializable
data class UiSwitchPayload(
    @SerialName("style") val style: String? = null,
    @SerialName("is_checked") val isChecked: Boolean? = null,
)
