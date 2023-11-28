package io.apiable.gateways.adapter.domain

/**
 * Apiable Oy
 * http://www.apiable.io/
 *
 * (c) Copyright Apiable Oy. All rights reserved.
 *
 * This product is the proprietary and sole property of Apiable Oy.
 */
interface UsageForKey : Integratable

data class AmazonUsageForKey(
    override var id: String,
    override var integrationId: String,
    val used: Number,
    val total: Number,
    val period: String
) : UsageForKey

interface UsageForPlan : Integratable

data class AmazonUsageForPlan(
    override var id: String,
    override var integrationId: String,
    var total: Long
) : UsageForPlan