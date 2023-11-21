package io.apiable.gateways.adapter

import io.apiable.gateways.adapter.domain.*

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

interface ApiGateway {

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
     * Update Plan on a Gateway
     *
     * Callout to:
     *    AWS: java-client: createUsagePlan
     *    Kong: Kong does not really have plans, but works with plugins configuration, so nothing to do here
     *    Azure: DELETE https://management.azure.com${plan.integrationId}?api-version=${conf.version}
     * */
    fun deletePlan(conf: Conf, plan: Plan)

    /**
     * Get Documentation for API
     *
     * Callout to:
     *    AWS: java-client: listDocumentation
     *    Kong: tbd
     *    Azure: tbd
     * */
    fun getPlanDocumentation(conf: Conf, id:String, version:String) : String

    /**
     * Get Usage for Subscription/Key
     *
     * Callout to:
     *    AWS: java-client: listTheUsageOfAKey
     *    Kong: not supported, client gets the usage withing the headers after every call
     *    Azure: tbd
     * */
    fun getUsageByApikey(conf: Conf, keyId: String): Usage

}

interface AuthGateway {
    /**
     * Create a Key
     *
     * AuthType.BASIC_API_KEY
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
     *
     * AuthType.INTERMEDIATE_PRE_GENERATE_TOKEN, INTERMEDIATE_CLIENT_CREDENTIAL, ADVANCED_CODE_FLOW
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

    fun createAuth(conf: Conf, auth: Auth, plans: List<String>): Auth
    /**
     *
     * AuthType.BASIC_API_KEY
     *
     * Callout to:
     *    AWS: java-client: deleteKey/createKey
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
     *
     *
     *
     * AuthType.INTERMEDIATE_PRE_GENERATE_TOKEN, INTERMEDIATE_CLIENT_CREDENTIAL, ADVANCED_CODE_FLOW
     *
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
    fun refreshAuth(conf: Conf, auth: Auth): Auth


    /**
     * Revoke the Key
     * AuthType.BASIC_API_KEY
     *
     * Callout to:
     *    AWS: java-client: deleteKey
     *    Kong: DELETE ${conf.url}/consumers/$username
     *    Azure: DELETE: https://management.azure.com${subscription.integrationId}?api-version=${conf.version}"
     *
     * AuthType.INTERMEDIATE_PRE_GENERATE_TOKEN, INTERMEDIATE_CLIENT_CREDENTIAL, ADVANCED_CODE_FLOW
     * Updates a Client on a Gateway
     *
     * Callout to:
     *    AWS: not supported: Can be combined with the Apiable Auth Platform - Curity
     *    Kong:
     *       DELETE: ${conf.url}/consumers/$username
     *    Azure:
     *       not supported yet: Can be combined with the Apiable Auth Platform - Curity
     * */
    fun revokeAuth(conf: Conf, auth: Auth)

    /**
     * Read the key
     * AuthType.BASIC_API_KEY
     *
     * Callout to:
     *    AWS: java-client: getKeyForSubscription
     *    Kong: tbd
     *    Azure: tbd
     *
     *
     *
     * AuthType.INTERMEDIATE_PRE_GENERATE_TOKEN, INTERMEDIATE_CLIENT_CREDENTIAL, ADVANCED_CODE_FLOW
     * Not implemented yet
     * */
    fun readAuth(conf: Conf, auth: Auth): Auth
}

interface Gateway: ApiGateway, AuthGateway



