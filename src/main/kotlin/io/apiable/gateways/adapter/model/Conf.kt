package io.apiable.gateways.adapter.model

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

/*
Auth Type description
Level 0 - Basic Auth - API Key
Level 1 - Intermediate - Server to Server - Pre-generated Token
Level 1 - Intermediate - Server to Server - OAuth 2.0: Client Credentials
Level 2 - Advanced - Mobile and Web Client - OAuth 2.0: Code Flow
Level 3 - Evolved - Mobile and Web Client - Centralized Claims
*/
enum class AuthType {
    BASIC_API_KEY,
    INTERMEDIATE_PRE_GENERATE_TOKEN,
    INTERMEDIATE_CLIENT_CREDENTIAL,
    ADVANCED_CODE_FLOW,
    EVOLVED_CENTRALIZED_CLAIMS
}

enum class AuthServerType {
    APIABLE, COGNITO, NATIVE
}

interface Authz {
    val type: AuthServerType
    val supportedAuthTypes: List<AuthType>
}

data class CognitoAuthzServer (
    val roleArn: String,
    val region: String,
    val userPoolId: String,
    val clientId: String,
    val clientSecret: String,
    override val supportedAuthTypes: List<AuthType>,
    override val type: AuthServerType = AuthServerType.COGNITO
): Authz

data class NativeAuthzServer (
    override val supportedAuthTypes: List<AuthType>,
    override val type: AuthServerType = AuthServerType.NATIVE
): Authz

data class ApiableAuthzServer (
    override val supportedAuthTypes: List<AuthType>,
    override val type: AuthServerType = AuthServerType.APIABLE
): Authz

interface Conf {
    var id: String
    var authz: Authz
}

interface AmazonConf: Conf {
    var region: String
    var account: String?
}

data class AmazonBasicConf(
    override var id: String,
    override var authz: Authz,
    override var region: String,
    var key: String,
    var secret: String,
    override var account: String? = null
) : AmazonConf


/*
 *
 * Amazon role arn conf
 *
 * @property type
 * @property roleArn
 * @property region The AWS region
 *           AP_SOUTH_1("ap-south-1"),EU_SOUTH_1("eu-south-1"),US_GOV_EAST_1("us-gov-east-1"),CA_CENTRAL_1("ca-central-1"),EU_CENTRAL_1("eu-central-1"),US_ISO_WEST_1("us-iso-west-1"),
 *           US_WEST_1("us-west-1"),US_WEST_2("us-west-2"),AF_SOUTH_1("af-south-1"),EU_NORTH_1("eu-north-1"),EU_WEST_3("eu-west-3"),EU_WEST_2("eu-west-2"),EU_WEST_1("eu-west-1"),AP_NORTHEAST_3("ap-northeast-3"),
 *           AP_NORTHEAST_2("ap-northeast-2"),AP_NORTHEAST_1("ap-northeast-1"),ME_SOUTH_1("me-south-1"),SA_EAST_1("sa-east-1"),AP_EAST_1("ap-east-1"),CN_NORTH_1("cn-north-1"),US_GOV_WEST_1("us-gov-west-1"),
 *           AP_SOUTHEAST_1("ap-southeast-1"),AP_SOUTHEAST_2("ap-southeast-2"),US_ISO_EAST_1("us-iso-east-1"),AP_SOUTHEAST_3("ap-southeast-3"),US_EAST_1("us-east-1"),US_EAST_2("us-east-2"),CN_NORTHWEST_1("cn-northwest-1"),
 *           US_ISOB_EAST_1("us-isob-east-1"),AWS_GLOBAL("aws-global"),AWS_CN_GLOBAL("aws-cn-global"),AWS_US_GOV_GLOBAL("aws-us-gov-global"),AWS_ISO_GLOBAL("aws-iso-global"),AWS_ISO_B_GLOBAL("aws-iso-b-global")
 *
 * @constructor Create empty Amazon role arn conf
 */
data class AmazonRoleArnConf(
    override var id: String,
    override var authz: Authz,
    override var region: String,
    val roleArn: String, // assume role arn,
    override var account: String? = null
) : AmazonConf

data class ApigeeJsonKeyConf(
    override var id: String,
    override var authz: Authz,
    var jsonkey: String,
    val organization: String
) : Conf

data class AzureBasicConf(
    override var id: String,
    override var authz: Authz,
    var clientid: String,
    var secret: String,
    val subscriptionid: String,
    val tenantid: String,
    val serviceid: String,
    val resourceGroup: String
) : Conf

data class KongBasicConf(
    override var id: String,
    override var authz: Authz,
    var key: String,
    var url: String
) : Conf