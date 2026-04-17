package io.apiable.authserver.adapter

enum class ScopeBindingErrorCode {
    NETWORK,        // connection refused, DNS failure, SSL handshake
    AUTH,           // 401/403, credentials rejected, admin creds missing or not configured
    INVALID_SCOPE,  // 400, malformed scope or unknown scope name at realm
    NOT_FOUND,      // 404, client or realm not found
    RATE_LIMIT,     // 429
    TIMEOUT,        // connect or request timeout exceeded
    UNKNOWN,        // 5xx or unexpected
}
