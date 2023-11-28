package io.apiable.gateways.adapter.domain

/**
 * Apiable Oy
 * http://www.apiable.io/
 *
 * (c) Copyright Apiable Oy. All rights reserved.
 *
 * This product is the proprietary and sole property of Apiable Oy.
 * Use, duplication or dissemination is subject to prior written consent of
 * Apiable Oy.
 *
 * Created on 26.04.23
 * @author: Apiable Geeks <geeks@apiable.io>
 *
 */
interface Auth: Integratable

interface AuthRead: Auth
interface AuthCreate: Auth {
    val plan: Plan
    val attributes: Map<String, Any>
}
interface AuthUpdate: Auth {
    val plan: Plan
    val attributes: Map<String, Any>
}
interface AuthRevoke: Auth
data class AuthRefresh (val revoke: AuthRevoke, val create: AuthCreate)
interface AuthClientCreate: AuthCreate {
    var appendToToken: Map<String,String>?
}
interface AuthClientUpdate: AuthUpdate {
    var appendToToken: Map<String,String>?
    var examples: CalloutExamples?
}
interface AuthClientRevoke: AuthRevoke {
    var registrationClientUri: String
}

data class CalloutExamples(val curl: String)

data class AuthBasicApiKey(
    override var id: String,
    override var integrationId: String,
    val key: String,
    val key2: String? = null
) : Auth

data class AuthBasicApiKeyRead(
    override var integrationId: String,
    override var id: String = integrationId
) : AuthRead

data class AuthBasicApiKeyCreate(
    override var id: String,
    override var integrationId: String,
    override val plan: Plan,
    override val attributes: Map<String,String> = emptyMap(),
    val key: String? = null,
    val key2: String? = null
) : AuthCreate, AuthUpdate

data class AuthBasicApiKeyRevoke(
    override var integrationId: String,
    override var id: String = integrationId
) : AuthRevoke

data class AuthIntermediatePreGenerateToken(
    override var id: String,
    override var integrationId: String,
    val appendToToken: Map<String,String>? = null,
    val token: String
) : Auth

data class AuthIntermediatePreGenerateTokenRead(
    override var integrationId: String,
    override var id: String = integrationId
) : AuthRead

data class AuthIntermediatePreGenerateTokenCreate(
    override var id: String,
    override var integrationId: String,
    override val plan: Plan,
    override val attributes: Map<String,String> = emptyMap(),
    val appendToToken: Map<String,String>? = null
) : AuthCreate, AuthUpdate

data class AuthIntermediatePreGenerateTokenRevoke(
    override var integrationId: String,
    override var id: String = integrationId,
    val token: String
) : AuthRevoke

// https://learn.microsoft.com/en-us/linkedin/shared/authentication/client-credentials-flow?context=linkedin%2Fcontext
data class AuthIntermediateClientCredential(
    override var id: String,
    override var integrationId: String,
    var clientId: String,
    var clientSecret: String,
    var registrationClientUri: String,
    var redirectUri: String,
    val appendToToken: Map<String,String>? = null,
    var examples: CalloutExamples? = null
) : Auth

data class AuthIntermediateClientCredentialRead(
    override var integrationId: String,
    override var id: String = integrationId
) : AuthRead

data class AuthIntermediateClientCredentialCreate(
    override var id: String,
    override var integrationId: String,
    override val plan: Plan,
    override val attributes: Map<String,String> = emptyMap(),
    var redirectUri: String,
    override var appendToToken: Map<String,String>? = null
) : AuthClientCreate

data class AuthIntermediateClientCredentialUpdate(
    override var id: String,
    override var integrationId: String,
    override val plan: Plan,
    override val attributes: Map<String, Any>,
    var clientId: String,
    var redirectUri: String,
    var registrationClientUri: String,
    override var examples: CalloutExamples? = null,
    override var appendToToken: Map<String,String>? = null,
) : AuthClientUpdate

data class AuthIntermediateClientCredentialRevoke(
    override var integrationId: String,
    override var id: String = integrationId,
    override var registrationClientUri: String
) : AuthClientRevoke

// https://learn.microsoft.com/en-us/linkedin/shared/authentication/authorization-code-flow?context=linkedin%2Fcontext&tabs=HTTPS1
data class AuthAdvancedCodeFlow(
    override var id: String,
    override var integrationId: String,
    var clientId: String,
    var clientSecret: String,
    var registrationClientUri: String,
    var redirectUris: List<String>,
    var allowedOrigins: List<String> = emptyList(),
    val postLogoutRedirectUris: List<String> = emptyList(),
    val appendToToken: Map<String,String>? = null,
    var examples: CalloutExamples? = null
) : Auth

data class AuthAdvancedCodeFlowRead(
    override var integrationId: String,
    override var id: String = integrationId
) : AuthRead

data class AuthAdvancedCodeFlowCreate(
    override var id: String,
    override var integrationId: String,
    override val plan: Plan,
    override val attributes: Map<String,String> = emptyMap(),
    var redirectUris: List<String>,
    var allowedOrigins: List<String> = emptyList(),
    val postLogoutRedirectUris: List<String> = emptyList(),
    override var appendToToken: Map<String,String>? = null
) : AuthClientCreate

data class AuthAdvancedCodeFlowUpdate(
    override var id: String,
    override var integrationId: String,
    override val plan: Plan,
    override val attributes: Map<String, Any>,
    var clientId: String,
    var redirectUris: List<String>,
    var allowedOrigins: List<String> = emptyList(),
    var registrationClientUri: String,
    val postLogoutRedirectUris: List<String> = emptyList(),
    override var examples: CalloutExamples? = null,
    override var appendToToken: Map<String,String>? = null
) : AuthClientUpdate

data class AuthAdvancedCodeFlowRevoke(
    override var integrationId: String,
    override var id: String = integrationId,
    override var registrationClientUri: String
) : AuthClientRevoke



