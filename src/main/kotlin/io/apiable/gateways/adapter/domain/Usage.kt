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
 */
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value= AmazonApi::class, name="AMAZON")
)
interface UsageForKey{
    var type: GatewayType
}

data class AmazonUsageForKey(
    override var type: GatewayType = GatewayType.AMAZON,
    val used: Number,
    val total: Number,
    val period: String
) : UsageForKey

@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include= JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value= AmazonApi::class, name="AMAZON")
)
interface UsageForPlan{
    var type: GatewayType
}

data class AmazonUsageForPlan(
    override var type: GatewayType = GatewayType.AMAZON,
    val name: String,
    val total: Long
) : UsageForPlan