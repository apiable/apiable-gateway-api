package io.apiable.authserver.adapter

sealed class ScopeBindingResult {
    data class Success(val scopes: List<String>) : ScopeBindingResult()
    data class Error(
        val code: ScopeBindingErrorCode,
        val message: String,
        val cause: Throwable? = null,
        val providerContext: Map<String, Any> = emptyMap(),
    ) : ScopeBindingResult()
}
