package io.apiable.gateways.adapter.domain

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


interface IPlan : Integratable

data class Plan(
    override var id: String,
    override var integrationId: String,
    val apis: List<Api>,
    val rateLimit: RateLimit? = null
): IPlan

data class PlanCreate(
    override var id: String,
    override var integrationId: String,
    val apis: List<Api>,
    val rateLimit: RateLimit? = null
): IPlan

data class PlanUpdate(
    override var id: String,
    override var integrationId: String,
    val rateLimit: RateLimit? = null,
    val apisToAdd: List<Api>,
    val apisToDelete: List<Api>
): IPlan

