package io.apiable.gateways.adapter.domain

import java.io.Serializable

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
 * Created on 26.11.23
 * @author: Apiable Geeks <geeks@apiable.io>
 *
 */
interface Integratable: Serializable{
    var id: String
    var integrationId: String
}
