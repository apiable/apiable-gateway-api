package io.apiable.authserver.adapter

/**
 * A scope as it exists at the Authorization Server provider.
 *
 * [includeInTokenScope] reflects whether the provider emits this scope into the access tokens it
 * issues. A scope can exist at the provider yet be silently inert — present in the catalog but
 * absent from every token's `scope` claim — in which case the resource server never sees the grant.
 * Null means the provider has no such concept (or it was not read); callers treat a non-false value
 * as "emitted". Keycloak maps it from the client scope's `include.in.token.scope` attribute.
 */
data class ProviderScope(
    val name: String,
    val description: String? = null,
    val includeInTokenScope: Boolean? = null,
)
