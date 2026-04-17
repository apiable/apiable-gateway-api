package io.apiable.authserver.adapter

/**
 * Provider-agnostic scope binding adapter.
 * Implementations live per provider in portal-backend under authserver/adapter/{provider}/.
 *
 * Called by:
 * - Scope grant lifecycle (Epic 5) for bind/unbind on approval/revocation
 * - Bulk scope import (Epic 2 Story 2.1) for readAllProviderScopes
 *
 * Every method returns [ScopeBindingResult] or [ProviderScopeListResult]. No raw exceptions
 * escape the adapter boundary (AR5).
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
