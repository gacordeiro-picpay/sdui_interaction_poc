package pic.poc.sdui.interaction.sdui.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UiScreenPayload(
    @SerialName("id") val id: String? = null,
    @SerialName("components") val components: List<UiComponentPayload>? = null,
    @SerialName("data") val data: Map<String, String>? = null,
    @SerialName("actions") val actions: Map<String, String>? = null,
    @SerialName("events") val events: Map<String, UiEventPayload>? = null,
)

@Serializable
data class UiComponentPayload(
    @SerialName("id") val id: String? = null,
    @SerialName("type") val type: String? = null,
)

@Serializable
data class UiEventPayload(
    @SerialName("event_name") val eventName: String? = null,
    @SerialName("event_properties") val eventProperties: Map<String, String>? = null,
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
