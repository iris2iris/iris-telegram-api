package iris.tg.irisjson.response

import iris.json.JsonItem
import iris.tg.api.response.SendMessageResponse
import iris.tg.irisjson.items.IrisJsonMessage

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonSendMessageResponse(source: JsonItem) : IrisJsonResponse(source), SendMessageResponse {
	override val result by lazyItemOrNull("result") { IrisJsonMessage(it) }
}