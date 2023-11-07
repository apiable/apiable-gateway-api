package io.apiable.gateways.adapter.models.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.apiable.gateways.adapter.models.conf.AuthType

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

enum class GrantType{
    CLIENT_CREDENTIAL,AUTH_CODE
}
class CalloutExamples(val curl: String)
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value= AuthBasicApiKey::class, name="BASIC_API_KEY"),
    JsonSubTypes.Type(value= AuthIntermediatePreGenerateToken::class, name="INTERMEDIATE_PRE_GENERATE_TOKEN"),
    JsonSubTypes.Type(value= AuthIntermediateClientCredential::class, name="INTERMEDIATE_CLIENT_CREDENTIAL"),
    JsonSubTypes.Type(value= AuthAdvancedCodeFlow::class, name="ADVANCED_CODE_FLOW")
)
interface Auth: java.io.Serializable{
    var type: AuthType
}

class AuthBasicApiKey(
    override var type: AuthType =AuthType.BASIC_API_KEY,
    var key: String,
    var key2: String? = null
) : Auth

class AuthIntermediatePreGenerateToken(
    override var type: AuthType = AuthType.INTERMEDIATE_PRE_GENERATE_TOKEN,
    var token: String
) : Auth


// https://learn.microsoft.com/en-us/linkedin/shared/authentication/client-credentials-flow?context=linkedin%2Fcontext
class AuthIntermediateClientCredential(
    override var type: AuthType = AuthType.INTERMEDIATE_CLIENT_CREDENTIAL,
    var clientId: String,
    var clientSecret: String,
    var registrationClientUri: String,
    var examples: CalloutExamples? = null
) : Auth

// https://learn.microsoft.com/en-us/linkedin/shared/authentication/authorization-code-flow?context=linkedin%2Fcontext&tabs=HTTPS1
class AuthAdvancedCodeFlow(
    override var type: AuthType = AuthType.ADVANCED_CODE_FLOW,
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
    var integrationId: String? = null,
    var auth: Auth? = null
)