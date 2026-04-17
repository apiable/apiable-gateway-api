package io.apiable.authserver.adapter

sealed class ProviderScopeListResult {
    data class Success(val scopes: List<ProviderScope>) : ProviderScopeListResult()
    data class Error(
        val code: ScopeBindingErrorCode,
        val message: String,
        val cause: Throwable? = null,
        val providerContext: Map<String, Any> = emptyMap(),
    ) : ProviderScopeListResult()
}
