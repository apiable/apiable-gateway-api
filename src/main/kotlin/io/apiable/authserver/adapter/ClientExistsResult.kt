package io.apiable.authserver.adapter

sealed class ClientExistsResult {
    data class Found(val clientId: String) : ClientExistsResult()
    data class NotFound(val clientId: String) : ClientExistsResult()
    data class Error(
        val code: ScopeBindingErrorCode,
        val message: String,
        val cause: Throwable? = null,
    ) : ClientExistsResult()
}