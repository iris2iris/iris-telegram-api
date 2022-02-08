package iris.tg.irisjson

import iris.tg.api.TgApiObject
import iris.tg.connection.Connection

open class TgApi_IrisJson(token: String, apiPath: String? = null, connection: Connection<String, ByteArray?>? = null
) : TgApiObject(token, ResponseHandler_IrisJsonObj(), apiPath, connection) {

}