package io.apiable.gateways.adapter

/**
 * Provider-agnostic Dynamic Client Registration client (RFC 7591 / RFC 7592).
 *
 * Implementations live per provider in portal-backend under gateways/adapter/{provider}/.
 * Callers always obtain an instance via DcrClientFactory — never reference implementation
 * classes directly (AR4).
 *
 * Every method returns [DcrResult]. No raw exceptions escape the adapter boundary (AR5).
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
