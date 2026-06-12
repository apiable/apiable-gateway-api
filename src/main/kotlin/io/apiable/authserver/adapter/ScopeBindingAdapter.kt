package io.apiable.authserver.adapter

/**
 * Provider-agnostic scope binding adapter.
 *
 * Implementations are discovered and invoked through this interface; callers should depend on
 * the contract defined here rather than any provider-specific implementation details.
 *
 * Every method reports success or failure via [ScopeBindingResult], [ProviderScopeListResult],
 * or [ClientExistsResult]. Implementations should convert operational failures into those
 * result types instead of allowing exceptions to propagate to callers. A result may still retain
 * an underlying [Throwable] as diagnostic context.
 *
 * @param integrationId String representation of the AuthzIntegration ObjectId.
 */
interface ScopeBindingAdapter {

    /**
     * Bind additional scopes to a client. Additive — does not remove existing scopes.
     * Idempotent: binding an already-bound scope is a no-op success.
     */
    fun bindScopes(integrationId: String, clientId: String, scopes: List<String>): ScopeBindingResult

    /**
     * Unbind scopes from a client. Subtractive — does not affect unrelated scopes.
     * Idempotent: unbinding a scope that is not bound is a no-op success.
     */
    fun unbindScopes(integrationId: String, clientId: String, scopes: List<String>): ScopeBindingResult

    /**
     * Read the current scopes bound to a client.
     */
    fun readScopes(integrationId: String, clientId: String): ScopeBindingResult

    /**
     * Read all scopes defined at the provider level (realm-level client scopes for Keycloak).
     * Used by Epic 2 Story 2.1 "Sync from Auth Server" bulk import.
     */
    fun readAllProviderScopes(integrationId: String): ProviderScopeListResult

    /**
     * Push Apiable-defined scope definitions into the provider's scope catalog —
     * creating what is missing and updating what differs. The push half of provider
     * scope management (the read half is [readAllProviderScopes]).
     *
     * Idempotent: a scope that already matches the incoming definition is left
     * untouched; an existing scope whose description differs is updated.
     *
     * @param resourceServerName Apiable-side grouping name (Resource Group). Providers
     *        with a native equivalent (Cognito Resource Server) SHOULD map it directly;
     *        providers without one (Keycloak) MAY treat it as opaque metadata — log it
     *        for traceability, but do NOT prefix scope names with it.
     * @param scopes the scope definitions to create or update.
     * @return [WriteScopesResult.Success] on partial or complete success — per-scope
     *         failures surface in [WriteScopesResult.Success.errors] without aborting
     *         the batch; [WriteScopesResult.Error] on total failure (auth, network),
     *         in which case nothing was written.
     * @throws UnsupportedOperationException if the adapter has not yet implemented the
     *         write path. Callers MUST catch this and surface it as a user-facing 400.
     */
    fun writeScopes(
        integrationId: String,
        resourceServerName: String,
        scopes: List<ProviderScope>,
    ): WriteScopesResult = throw UnsupportedOperationException(
        "writeScopes not yet supported for ${this::class.simpleName}",
    )

    /**
     * Check whether a client with the given OAuth2 clientId is registered with the provider.
     * Returns [ClientExistsResult.Found] if present, [ClientExistsResult.NotFound] if absent,
     * or [ClientExistsResult.Error] if the check itself could not complete (auth/network failure).
     */
    fun clientExists(integrationId: String, clientId: String): ClientExistsResult
}
