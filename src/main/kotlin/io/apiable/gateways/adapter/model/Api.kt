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
 * Api
 *
 * @property name The name of the Api
 * @property url The url of the API
 * @property method The HTTP Method of the API
 * @property integrationId
 * @constructor Create empty Api
 */
data class Resource(
    var path: String,
    var method: String,
    var integrationId: String
)

interface Api{
    var integrationId: String
    var serviceIntegrationId: String
    var name: String
    var url: String
    var resources: List<Resource>?
}

data class AzureApi(
    override var integrationId: String,
    override var serviceIntegrationId: String,
    override var name: String,
    override var url: String,
    override var resources: List<Resource>? = emptyList(),
) : Api

data class AmazonApi(
    override var integrationId: String,
    override var serviceIntegrationId: String,
    override var name: String,
    override var url: String,
    override var resources: List<Resource>? = emptyList(),
    var stage: String,
    var environmentId: String? = null
) : Api

data class KongApi(
    override var integrationId: String,
    override var serviceIntegrationId: String,
    override var name: String,
    override var url: String,
    override var resources: List<Resource>? = emptyList(),
) : Api

data class ApigeeApi(
    override var integrationId: String,
    override var serviceIntegrationId: String,
    override var name: String,
    override var url: String,
    override var resources: List<Resource>? = emptyList(),
    var environment: String,
    var revision: String
) : Api
