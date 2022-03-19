package iris.tg.irisjson

import iris.tg.api.TgApiObjFuture
import iris.connection.Connection
import java.util.concurrent.CompletableFuture

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TgApiFuture_IrisJson(token: String, apiPath: String? = null, connection: Connection<CompletableFuture<String>, CompletableFuture<ByteArray?>>? = null
) : TgApiObjFuture(token, ResponseHandler_IrisJsonObj(), apiPath, connection) {

}