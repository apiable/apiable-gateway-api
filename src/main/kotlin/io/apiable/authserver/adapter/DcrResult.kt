package io.apiable.authserver.adapter

sealed class DcrResult {
    data class Success(val registration: DcrClientRegistration) : DcrResult()
    data class Error(
        val code: DcrErrorCode,
        val message: String,
        val cause: Throwable? = null,
        val providerContext: Map<String, Any> = emptyMap(),
    ) : DcrResult()
}
