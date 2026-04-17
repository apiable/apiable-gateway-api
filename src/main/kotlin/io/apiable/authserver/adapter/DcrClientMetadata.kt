package io.apiable.authserver.adapter

/**
 * Client metadata for DCR register/update calls. Field names mirror RFC 7591.
 */
data class DcrClientMetadata(
    val clientName: String,
    val scope: List<String> = emptyList(),
    val grantTypes: List<String> = listOf("client_credentials"),
    val tokenEndpointAuthMethod: String = "client_secret_basic",
    val redirectUris: List<String> = emptyList(),
    /** Provider-specific fields serialised alongside standard fields in the request body. */
    val providerExtras: Map<String, Any> = emptyMap(),
)
