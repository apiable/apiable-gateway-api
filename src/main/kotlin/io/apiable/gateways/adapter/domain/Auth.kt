package io.apiable.gateways.adapter.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

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

@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value= AuthBasicApiKey::class, name="BASIC_API_KEY"),
    JsonSubTypes.Type(value= AuthIntermediatePreGenerateToken::class, name="INTERMEDIATE_PRE_GENERATE_TOKEN"),
    JsonSubTypes.Type(value= AuthIntermediateClientCredential::class, name="INTERMEDIATE_CLIENT_CREDENTIAL"),
    JsonSubTypes.Type(value= AuthAdvancedCodeFlow::class, name="ADVANCED_CODE_FLOW")
)
interface Auth: java.io.Serializable{
    var type: AuthType
    var integrationId: String
}

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
interface AuthRefresh: java.io.Serializable {
    val revoke: AuthRevoke
    val create: AuthCreate
}

interface AuthClientCreate: AuthCreate {
    var appendToToken: Map<String,String>?
}
interface AuthClientUpdate: AuthUpdate {
    var appendToToken: Map<String,String>?
}

class CalloutExamples(val curl: String)

data class AuthBasicApiKey(
    override var type: AuthType = AuthType.BASIC_API_KEY,
    override var integrationId: String,
    val key: String,
    val key2: String? = null
) : Auth

data class AuthBasicApiKeyRead(
    override var type: AuthType = AuthType.BASIC_API_KEY,
    override var integrationId: String
) : AuthRead

data class AuthBasicApiKeyCreate(
    override var type: AuthType = AuthType.BASIC_API_KEY,
    override var integrationId: String,
    override val plan: Plan,
    override val attributes: Map<String,String> = emptyMap(),
    val key: String? = null,
    val key2: String? = null
) : AuthCreate, AuthUpdate

data class AuthBasicApiKeyRevoke(
    override var type: AuthType = AuthType.BASIC_API_KEY,
    override var integrationId: String
) : AuthRevoke

data class AuthIntermediatePreGenerateToken(
    override var type: AuthType = AuthType.INTERMEDIATE_PRE_GENERATE_TOKEN,
    override var integrationId: String,
    val appendToToken: Map<String,String>? = null,
    val token: String
) : Auth

data class AuthIntermediatePreGenerateTokenRead(
    override var type: AuthType = AuthType.INTERMEDIATE_PRE_GENERATE_TOKEN,
    override var integrationId: String
) : AuthRead

data class AuthIntermediatePreGenerateTokenCreate(
    override var type: AuthType = AuthType.INTERMEDIATE_PRE_GENERATE_TOKEN,
    override var integrationId: String,
    override val plan: Plan,
    override val attributes: Map<String,String> = emptyMap(),
    val appendToToken: Map<String,String>? = null
) : AuthCreate, AuthUpdate

data class AuthIntermediatePreGenerateTokenRevoke(
    override var type: AuthType = AuthType.INTERMEDIATE_PRE_GENERATE_TOKEN,
    override var integrationId: String,
    val token: String
) : AuthRevoke

// https://learn.microsoft.com/en-us/linkedin/shared/authentication/client-credentials-flow?context=linkedin%2Fcontext
data class AuthIntermediateClientCredential(
    override var type: AuthType = AuthType.INTERMEDIATE_CLIENT_CREDENTIAL,
    override var integrationId: String,
    var clientId: String,
    var clientSecret: String,
    var registrationClientUri: String,
    var redirectUri: String,
    val appendToToken: Map<String,String>? = null,
    var examples: CalloutExamples? = null
) : Auth

data class AuthIntermediateClientCredentialRead(
    override var type: AuthType = AuthType.INTERMEDIATE_CLIENT_CREDENTIAL,
    override var integrationId: String
) : AuthRead

data class AuthIntermediateClientCredentialCreate(
    override var type: AuthType = AuthType.INTERMEDIATE_CLIENT_CREDENTIAL,
    override var integrationId: String,
    override val plan: Plan,
    override val attributes: Map<String,String> = emptyMap(),
    var redirectUri: String,
    override var appendToToken: Map<String,String>? = null
) : AuthClientCreate

data class AuthIntermediateClientCredentialUpdate(
    override var type: AuthType = AuthType.INTERMEDIATE_CLIENT_CREDENTIAL,
    override var integrationId: String,
    override val plan: Plan,
    override val attributes: Map<String, Any>,
    var clientId: String,
    var redirectUri: String,
    var registrationClientUri: String,
    override var appendToToken: Map<String,String>? = null,
) : AuthClientUpdate

data class AuthIntermediateClientCredentialRevoke(
    override var type: AuthType = AuthType.INTERMEDIATE_CLIENT_CREDENTIAL,
    override var integrationId: String,
    var registrationClientUri: String
) : AuthRevoke

// https://learn.microsoft.com/en-us/linkedin/shared/authentication/authorization-code-flow?context=linkedin%2Fcontext&tabs=HTTPS1
data class AuthAdvancedCodeFlow(
    override var type: AuthType = AuthType.ADVANCED_CODE_FLOW,
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
    override var type: AuthType = AuthType.ADVANCED_CODE_FLOW,
    override var integrationId: String
) : AuthRead

data class AuthAdvancedCodeFlowCreate(
    override var type: AuthType = AuthType.ADVANCED_CODE_FLOW,
    override var integrationId: String,
    override val plan: Plan,
    override val attributes: Map<String,String> = emptyMap(),
    var redirectUris: List<String>,
    var allowedOrigins: List<String> = emptyList(),
    val postLogoutRedirectUris: List<String> = emptyList(),
    override var appendToToken: Map<String,String>? = null
) : AuthClientCreate

data class AuthAdvancedCodeFlowUpdate(
    override var type: AuthType = AuthType.ADVANCED_CODE_FLOW,
    override var integrationId: String,
    override val plan: Plan,
    override val attributes: Map<String, Any>,
    var clientId: String,
    var redirectUris: List<String>,
    var allowedOrigins: List<String> = emptyList(),
    var registrationClientUri: String,
    val postLogoutRedirectUris: List<String> = emptyList(),
    override var appendToToken: Map<String,String>? = null
) : AuthClientUpdate

data class AuthAdvancedCodeFlowRevoke(
    override var type: AuthType = AuthType.INTERMEDIATE_CLIENT_CREDENTIAL,
    override var integrationId: String,
    var registrationClientUri: String
) : AuthRevoke
