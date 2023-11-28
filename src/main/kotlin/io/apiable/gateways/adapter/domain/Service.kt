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
 * Api
 *
 * @property name The name of the Api
 * @property url The url of the API
 * @property method The HTTP Method of the API
 * @property integrationId
 * @constructor Create empty Api
 */
interface Service : Integratable {
    var name: String
    var url: String
    var apis: List<Api>?
}

data class AmazonService(
    override var id: String,
    override var integrationId: String,
    override var name: String,
    override var url: String,
    override var apis: List<Api>? = null,
    val stage: String,
    var environmentId: String? = null
) : Service

data class AzureService(
    override var id: String,
    override var integrationId: String,
    override var name: String,
    override var url: String,
    override var apis: List<Api>? = null
) : Service

data class KongService(
    override var id: String,
    override var integrationId: String,
    override var name: String,
    override var url: String,
    override var apis: List<Api>? = null
) : Service

data class ApigeeService(
    override var id: String,
    override var integrationId: String,
    override var name: String,
    override var url: String,
    override var apis: List<Api>? = null,
    var environment: String,
    var revision: String
) : Service
