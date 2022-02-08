package iris.tg.py.response

import iris.json.JsonItem
import iris.tg.api.TgApiObjFuture
import iris.tg.api.response.SendMessageResponse
import iris.tg.irisjson.items.IrisJsonMessage
import iris.tg.irisjson.response.IrisJsonResponse
import iris.tg.py.items.PyMessage

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class PySendMessageResponse(api: TgApiObjFuture, source: JsonItem) : IrisJsonResponse(source), SendMessageResponse, PyResponse {
	override val result by lazyItemOrNull("result") { PyMessage(api, it) }
}