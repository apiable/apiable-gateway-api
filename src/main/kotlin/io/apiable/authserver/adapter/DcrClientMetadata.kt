package io.apiable.authserver.adapter

/**
 * Kotlin-domain model for DCR register/update metadata (RFC 7591 §2).
 *
 * Property names follow Kotlin conventions (camelCase). Implementations are responsible
 * for serialising each field to its RFC 7591 snake_case wire name (e.g. [clientName] →
 * `client_name`, [grantTypes] → `grant_types`). [scope] is modelled as a list for
 * convenience; the spec defines it as a single space-delimited string on the wire.
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