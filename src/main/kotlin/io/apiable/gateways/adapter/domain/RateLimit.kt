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
    JsonSubTypes.Type(value= AmazonRateLimit::class, name="AMAZON"),
    JsonSubTypes.Type(value= KongRateLimit::class, name="KONG"),
    JsonSubTypes.Type(value= AzureRateLimit::class, name="AZURE"),
    JsonSubTypes.Type(value= ApigeeRateLimit::class, name="APIGEE")
)
interface RateLimit{
    var type: GatewayType
}

data class AmazonRateLimit(
    override var type: GatewayType = GatewayType.AMAZON,
    var quoteLimit: Int? = null,
    var quotePeriod: AmazonApiLimitQuoteUnit? = null,
    var throttleBurstLimit: Int? = null,
    var throttleRateLimit: Double? = null,
) : RateLimit


data class KongRateLimit(
    override var type: GatewayType = GatewayType.KONG,
    val second: Long? = null,
    val hour: Long? = null,
    val minute: Long? = null,
    val day: Long? = null,
    val month: Long? = null,
    val year: Long? = null
) : RateLimit


data class AzureRateLimit(
    //https://learn.microsoft.com/en-gb/azure/api-management/rate-limit-by-key-policy
    override var type: GatewayType = GatewayType.AZURE,
    val rateLimitCalls: Long,
    val rateLimitRenewalPeriodInSec: Long,
    val rateLimitIncrementCondition: String = "@(context.Response.StatusCode == 200)", // "@(context.Response.StatusCode == 200)"
    val rateLimitCounterKey: String = "@(context.Subscription?.Key ?? \"anonymous\")", // "@(context.Request.IpAddress)"
    val rateLimitRemainingCallsVariableName: String, //"remainingCallsPerIP"
    //https://learn.microsoft.com/en-gb/azure/api-management/quota-by-key-policy
    val quotaCalls: Long,
    val quotaBandwidth: Long,
    val quotaRenewalPeriod: Long,
    val quotaIncrementCondition: String = "@(context.Response.StatusCode >= 200 && context.Response.StatusCode < 400)", //"@(context.Response.StatusCode >= 200 && context.Response.StatusCode < 400)"
    val quotaCounterKey: String = "@(context.Subscription?.Key ?? \"anonymous\")" // "@(context.Request.IpAddress)"
) : RateLimit

// https://cloud.google.com/apigee/docs/reference/apis/apigee/rest/v1/organizations.apiproducts#ApiProduct§
data class ApigeeRateLimit(
    override var type: GatewayType = GatewayType.APIGEE,
    val quota: Long,
    val quotaInterval: Long,
    val quotaTimeUnit: ApigeeApiLimitQuoteUnit
) : RateLimit

// https://cloud.google.com/apigee/docs/reference/apis/apigee/rest/v1/organizations.apiproducts#ApiProduct
enum class ApigeeApiLimitQuoteUnit{
    minute, hour, day, month
}

enum class AmazonApiLimitQuoteUnit{
    WEEK, MONTH, DAY
}