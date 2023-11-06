package io.apiable.gateways.adapter

import io.apiable.gateways.adapter.models.conf.Conf
import io.apiable.gateways.adapter.models.domain.*

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

interface GatewayAdapter {
    /**
     * List services of the Gateway
     *
     * Callout to:
     *    AWS: java-client: listStages of listApis
     *    Kong: GET ${conf.url}/services: {id, name, host, protocol, port, created, updated}
     *    Azure: https://management.azure.com/subscriptions/${conf.subscriptionid}/providers/Microsoft.ApiManagement/service?api-version=${conf.version}"
     * */
    fun listServices(conf: Conf): List<Service>

    /**
     * List apis of the Gateway
     *
     * Callout to:
     *    AWS: java-client: listStages of listApis
     *    Kong: GET ${conf.url}/services:
     *      response: {id, name, host, protocol, port, created, updated}
     *    Azure: GET https://management.azure.com/subscriptions/${conf.subscriptionid}/providers/Microsoft.ApiManagement/service?api-version=${conf.version}"
     * */
    fun listApis(conf: Conf, service: Service): List<Api>

    /**
     * Update Plan on a Gateway
     *
     * Callout to:
     *    AWS: java-client: updateUsagePlan
     *    Kong:
     *       GET ${conf.url}/services/${api.name}/plugins:
     *          response: {id, name, config}
     *       PATCH {conf.url}/services/${api.name}/plugins/${aclPlugin.id}:
     *          request: {config: {allow: ["$planId"]}}
     *          response: {id, config: {allow: ["$planId"]}}
     *    Azure:
     *       PUT https://management.azure.com${plan.integrationId}?api-version=${conf.version}
     *       DELETE https://management.azure.com${plan.integrationId}/apis/${api.name}?api-version=${conf.version}
     *       PUT https://management.azure.com${plan.integrationId}/apis/${api.name}?api-version=${conf.version}
     *       PUT https://management.azure.com${plan.integrationId}/policies/policy?api-version=${conf.version}
     * */
    fun updatePlan(conf: Conf, plan: Plan): Plan

    /**
     * Create Plan on a Gateway
     *
     * Callout to:
     *    AWS: java-client: createUsagePlan
     *    Kong: POST ${conf.url}/services/${api.name}/plugins:  {config: {allow: ["$planId"]}}
     *    Azure:
     *       PUT https://management.azure.com${plan.integrationId}?api-version=${conf.version}
     *       DELETE https://management.azure.com${plan.integrationId}/apis/${api.name}?api-version=${conf.version}
     *       PUT https://management.azure.com${plan.integrationId}/apis/${api.name}?api-version=${conf.version}
     *       PUT https://management.azure.com${plan.integrationId}/policies/policy?api-version=${conf.version}
     * */
    fun createPlan(conf: Conf, plan: Plan): Plan

    /**
     * Update Plan on a Gateway
     *
     * Callout to:
     *    AWS: java-client: createUsagePlan
     *    Kong: Kong does not really have plans, but works with plugins configuration, so nothing to do here
     *    Azure: DELETE https://management.azure.com${plan.integrationId}?api-version=${conf.version}
     * */
    fun deletePlan(conf: Conf, plan: Plan)

    /**
     * Ping the Gateway
     *
     * Callout to:
     *    AWS: java-client: listApis
     *    Kong: GET ${conf.url}/services
     *      response: {id, name, host, protocol, port, created, updated}
     *    Azure: GET https://management.azure.com/subscriptions/${conf.subscriptionid}/providers/Microsoft.ApiManagement/service?api-version=${conf.version}"
     * */
    fun ping(conf: Conf): Boolean

    /**
     * Get Documentation for API
     *
     * Callout to:
     *    AWS: java-client: listDocumentation
     *    Kong: tbd
     *    Azure: tbd
     * */
    fun getDocumentation(conf: Conf, id:String, version:String) : String

    /**
     * Get Usage for Subscription/Key
     *
     * Callout to:
     *    AWS: java-client: listTheUsageOfAKey
     *    Kong: not supported, client gets the usage withing the headers after every call
     *    Azure: tbd
     * */
    fun getApiKeyUsage(conf: Conf, subscription: Subscription): ApiKeyUsage

    /**
     * Create a Key
     * Callout to:
     *    AWS: java-client: createKeyForUsagePlan
     *    Kong:
     *       POST ${conf.url}/consumers:
     *          request: {username}
     *          response: {id, username, created, customId, tags}
     *       POST ${conf.url}/consumers/$username/key-auth:
     *          request: {key}
     *          response: {id, key, tags, ttl, created, consumer: {id}}
     *       POST ${conf.url}/consumers/$username/acls:
     *          request: {group}
     *          response: http.body ignored
     *       POST ${conf.url}/consumers/$username/plugins:
     *          request: {name, config: {second, minute, hour, day, month, year}}
     *          response: http.body ignored
     *    Azure:
     *       PUT https://management.azure.com$url/subscriptions/${subscription.id}?api-version=${conf.version}&appType=portal
     *
     * */
    fun createKey(conf: Conf, subscription: Subscription, key: String? = null, clientIdOverride: String? = null, clientSecretOverride: String? = null, appendToToken: Map<String,String>? = null): String

    /**
     * Read the key
     *
     * Callout to:
     *    AWS: java-client: getKeyForSubscription
     *    Kong: tbd
     *    Azure: tbd
     * */
    fun readKey(conf: Conf, subscription: Subscription): String

    /**
     * Revoke the Key
     *
     * Callout to:
     *    AWS: java-client: deleteKey
     *    Kong: DELETE ${conf.url}/consumers/$username
     *    Azure: DELETE: https://management.azure.com${subscription.integrationId}?api-version=${conf.version}"
     * */
    fun revokeKey(conf: Conf, key: String, clientIdOverride: String? = null, clientSecretOverride: String? = null)

    /**
     * Revoke the Key
     *
     * Callout to:
     *    AWS: java-client: deleteKey
     *    Kong:
     *       DELETE ${conf.url}/consumers/$username
     *       POST ${conf.url}/consumers:
     *          request: {username}
     *          response: {id, username, created, customId, tags}
     *       POST ${conf.url}/consumers/$username/key-auth:
     *          request: {key}
     *          response: {id, key, tags, ttl, created, consumer: {id}}
     *       POST ${conf.url}/consumers/$username/acls:
     *          request: {group}
     *          response: http.body ignored
     *       POST ${conf.url}/consumers/$username/plugins:
     *          request: {name, config: {second, minute, hour, day, month, year}}
     *          response: http.body ignored
     *    Azure:
     *       DELETE: https://management.azure.com${subscription.integrationId}?api-version=${conf.version}
     *       PUT https://management.azure.com$url/subscriptions/${subscription.id}?api-version=${conf.version}&appType=portal
     * */
    fun refreshKey(conf: Conf, key: String, clientIdOverride: String? = null, clientSecretOverride: String? = null, appendToToken: Map<String,String>? = null): String

    /**
     * Creates a Client on a Gateway
     *
     * Callout to:
     *    AWS: not supported: Can be combined with the Apiable Auth Platform - Curity
     *    Kong:
     *       POST ${conf.url}/consumers
     *       POST ${conf.url}/consumers/$username/oauth2/
     *       POST ${conf.url}/consumers/$username/acls
     *       GET ${conf.url}/services/${api.name}/routes
     *    Azure:
     *       not supported yet: Can be combined with the Apiable Auth Platform - Curity
     * */
    fun createClient(conf: Conf, grantType: GrantType, redirectUris: List<String>? = null): Client

    /**
     * Updates a Client on a Gateway
     *
     * Callout to:
     *    AWS: not supported: Can be combined with the Apiable Auth Platform - Curity
     *    Kong:
     *       PATCH: client.registrationClientUri, which is of a structure: ${conf.url}/consumers/$username/oauth2/${app.id}
     *          request: {name, redirectUris}
     *          response: {id, created, clientId, clientSecret, hashSecret, clientType, redirectUris, tags, name, consumer: {id}}
     *    Azure:
     *       not supported yet: Can be combined with the Apiable Auth Platform - Curity
     * */
    fun updateClient(conf: Conf, client: Client): Client

    /**
     * Updates a Client on a Gateway
     *
     * Callout to:
     *    AWS: not supported: Can be combined with the Apiable Auth Platform - Curity
     *    Kong:
     *       DELETE: ${conf.url}/consumers/$username
     *    Azure:
     *       not supported yet: Can be combined with the Apiable Auth Platform - Curity
     * */
    fun deleteClient(conf: Conf, client: Client)
}



