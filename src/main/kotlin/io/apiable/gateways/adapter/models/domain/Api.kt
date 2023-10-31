package io.apiable.gateways.adapter.models.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.apiable.gateways.adapter.models.conf.GatewayType

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
 * Api
 *
 * @property name The name of the Api
 * @property url The url of the API
 * @property method The HTTP Method of the API
 * @property integrationId
 * @constructor Create empty Api
 */
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value= AmazonApi::class, name="AMAZON"),
    JsonSubTypes.Type(value= KongApi::class, name="KONG"),
    JsonSubTypes.Type(value= AzureApi::class, name="AZURE"),
    JsonSubTypes.Type(value= ApigeeApi::class, name="APIGEE")
)
interface Api{
    var name: String
    var url: String
    val integrationId: String
    var type: GatewayType
}

class AzureApi(
    override var type: GatewayType = GatewayType.AZURE,
    override var name: String,
    override var url: String,
    override var integrationId: String,
) : Api

class AmazonApi(
    override var type: GatewayType = GatewayType.AMAZON,
    override var name: String,
    override var url: String,
    override var integrationId: String,
    val stage: String,
    var environmentId: String? = null
) : Api

class KongApi(
    override var type: GatewayType = GatewayType.KONG,
    override var name: String,
    override var url: String,
    override var integrationId: String,
) : Api

class ApigeeApi(
    override var type: GatewayType = GatewayType.APIGEE,
    override var name: String,
    override var url: String,
    override var integrationId: String,
) : Api