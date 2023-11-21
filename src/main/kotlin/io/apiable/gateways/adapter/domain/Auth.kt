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

class CalloutExamples(val curl: String)

data class AuthBasicApiKey(
    override var type: AuthType = AuthType.BASIC_API_KEY,
    override var integrationId: String,
    val key: String,
    val key2: String? = null
) : Auth

data class AuthIntermediatePreGenerateToken(
    override var type: AuthType = AuthType.INTERMEDIATE_PRE_GENERATE_TOKEN,
    override var integrationId: String,
    val token: String,
    val appendToToken: Map<String,String>? = null
) : Auth


// https://learn.microsoft.com/en-us/linkedin/shared/authentication/client-credentials-flow?context=linkedin%2Fcontext
data class AuthIntermediateClientCredential(
    override var type: AuthType = AuthType.INTERMEDIATE_CLIENT_CREDENTIAL,
    override var integrationId: String,
    var clientId: String,
    var clientSecret: String,
    var registrationClientUri: String,
    var redirectUri: String,
    var examples: CalloutExamples? = null
) : Auth

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
    var examples: CalloutExamples? = null
) : Auth

data class Subscription(
    val id: String,
    val integrationId: String? = null,
    var auth: Auth? = null
)