package io.apiable.authserver.adapter

/**
 * Provider-agnostic scope binding adapter.
 *
 * Implementations are discovered and invoked through this interface; callers should depend on
 * the contract defined here rather than any provider-specific implementation details.
 *
 * Every method reports success or failure via [ScopeBindingResult] or
 * [ProviderScopeListResult]. Implementations should convert operational failures into those
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
}
