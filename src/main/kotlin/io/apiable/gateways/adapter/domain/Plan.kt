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
data class Plan(
    val id: String,
    var integrationId: String,
    val apis: List<Api>,
    val rateLimit: RateLimit? = null
)

data class PlanCreate(
    val id: String,
    val apis: List<Api>,
    val rateLimit: RateLimit? = null
)

