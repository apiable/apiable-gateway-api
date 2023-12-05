package io.apiable.gateways.adapter.model

/**
 * Apiable Oy
 * http://www.apiable.io/
 *
 * (c) Copyright Apiable Oy. All rights reserved.
 *
 * This product is the proprietary and sole property of Apiable Oy.
 */
interface UsageForKey {
    var integrationId: String
}

data class AmazonUsageForKey(
    override var integrationId: String,
    val used: Number,
    val total: Number,
    val period: String
) : UsageForKey

interface UsageForPlan {
    var integrationId: String
}

data class AmazonUsageForPlan(
    override var integrationId: String,
    var total: Long
) : UsageForPlan