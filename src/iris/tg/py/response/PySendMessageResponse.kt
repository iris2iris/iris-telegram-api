package iris.tg.py.response

import iris.json.JsonItem
import iris.tg.api.response.SendMessageResponse
import iris.tg.irisjson.response.IrisJsonResponse
import iris.tg.py.Bot
import iris.tg.py.items.PyMessage

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class PySendMessageResponse(source: JsonItem) : IrisJsonResponse(source), SendMessageResponse, PyResponse {
	override val result by lazyItemOrNull("result") { PyMessage(null, it) }
}