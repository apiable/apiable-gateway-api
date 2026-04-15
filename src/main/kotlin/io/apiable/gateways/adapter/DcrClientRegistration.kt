package io.apiable.gateways.adapter

/**
 * Registered client details returned from any DCR operation.
 */
data class DcrClientRegistration(
    val clientId: String,
    /** Only populated on register/update; may be null on read if the provider doesn't expose it. */
    val clientSecret: String?,
    /** Management token for subsequent GET/PUT/DELETE on this specific client. Keycloak may rotate it on each call — always persist the latest value. */
    val registrationAccessToken: String?,
    val scope: List<String>,
    /** Token endpoint URL the consumer will call to obtain access tokens. */
    val tokenEndpoint: String,
    /** Provider-specific fields (Keycloak: client UUID, protocol, etc.). */
    val providerMetadata: Map<String, Any> = emptyMap(),
)