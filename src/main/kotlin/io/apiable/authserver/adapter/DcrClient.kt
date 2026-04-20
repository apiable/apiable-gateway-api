package io.apiable.authserver.adapter

/**
 * Provider-agnostic Dynamic Client Registration client (RFC 7591 / RFC 7592).
 *
 * Every method returns [DcrResult], so callers should handle outcomes through the result type
 * rather than relying on provider-specific implementation details.
 *
 * @param integrationId String representation of the AuthzIntegration ObjectId.
 */
interface DcrClient {

    /** Register a new OAuth2 client. NOT idempotent — calling twice creates two clients. */
    fun register(integrationId: String, metadata: DcrClientMetadata): DcrResult

    /**
     * Read a registered client. The returned [DcrClientRegistration] may contain a rotated
     * [DcrClientRegistration.registrationAccessToken] — callers must persist the latest value.
     */
    fun read(integrationId: String, clientId: String, registrationAccessToken: String): DcrResult

    /**
     * Update a registered client (e.g. scope change). Idempotent on request content.
     * The returned registration reflects the updated state including any rotated RAT.
     */
    fun update(
        integrationId: String,
        clientId: String,
        registrationAccessToken: String,
        metadata: DcrClientMetadata,
    ): DcrResult

    /**
     * Delete a registered client. Idempotent — a 404 from the provider is treated as
     * [DcrResult.Success] (already gone).
     */
    fun delete(integrationId: String, clientId: String, registrationAccessToken: String): DcrResult
}