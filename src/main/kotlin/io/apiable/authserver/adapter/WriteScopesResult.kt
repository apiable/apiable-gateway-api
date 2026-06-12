package io.apiable.authserver.adapter

/**
 * Outcome of pushing scope definitions into a provider's scope catalog via
 * [ScopeBindingAdapter.writeScopes].
 *
 * [Success] covers both complete and partial success: a partial failure of one
 * scope mid-batch surfaces in [Success.errors] rather than aborting the batch, so
 * every scope that could be written is still committed. [Error] is reserved for a
 * total failure where nothing was written (authentication failure, transport loss).
 */
sealed class WriteScopesResult {
    data class Success(
        val createdCount: Int,
        val updatedCount: Int,
        val deletedCount: Int,
        val conflictCount: Int,
        val errors: List<WriteScopeError> = emptyList(),
    ) : WriteScopesResult()

    data class Error(
        val code: ScopeBindingErrorCode,
        val message: String,
        val cause: Throwable? = null,
        val providerContext: Map<String, Any> = emptyMap(),
    ) : WriteScopesResult()
}

/**
 * A single scope that the provider rejected while the surrounding batch succeeded.
 */
data class WriteScopeError(
    val scopeName: String,
    val reason: String,
)
