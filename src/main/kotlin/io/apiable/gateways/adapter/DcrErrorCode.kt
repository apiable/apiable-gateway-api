package io.apiable.gateways.adapter

enum class DcrErrorCode {
    NETWORK,         // connection refused, DNS failure, SSL handshake
    AUTH,            // 401, 403, credentials rejected
    INVALID_REQUEST, // 400, malformed metadata
    NOT_FOUND,       // 404
    RATE_LIMIT,      // 429
    TIMEOUT,         // connect or request timeout exceeded
    UNKNOWN,         // 5xx or unexpected
}