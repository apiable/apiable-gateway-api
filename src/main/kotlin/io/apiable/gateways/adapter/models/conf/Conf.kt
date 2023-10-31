package io.apiable.gateways.adapter.models.conf

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
import com.fasterxml.jackson.annotation.*

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value=AmazonBasicConf::class, name="AMAZON_BASIC"),
    JsonSubTypes.Type(value=AmazonRoleArnConf::class, name="AMAZON_ROLE_ARN"),
    JsonSubTypes.Type(value=KongBasicConf::class, name="KONG_BASIC")
)
interface Conf: java.io.Serializable{
    var type: GatewayConnectionType
}

class AmazonBasicConf(
    override var type: GatewayConnectionType = GatewayConnectionType.AMAZON_BASIC,
    val key: String,
    val secret: String,
    val region: String,
) : Conf

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
class AmazonRoleArnConf(
    override var type: GatewayConnectionType = GatewayConnectionType.AMAZON_ROLE_ARN,
    val roleArn: String, // assume role arn
    val region: String,
) : Conf

class AzureBasicConf(
    override var type: GatewayConnectionType = GatewayConnectionType.AZURE_BASIC,
    val key: String,
    val secret: String,
    val subscriptionid: String,
    val tenantid: String,
) : Conf

class KongBasicConf(
    override var type: GatewayConnectionType = GatewayConnectionType.KONG_BASIC,
    val key: String,
    var url: String
) : Conf