package iris.tg.irisjson.response

import iris.json.JsonItem
import iris.tg.api.items.Chat
import iris.tg.api.response.GetChatResponse
import iris.tg.irisjson.items.IrisJsonChat

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonGetChatResponse(sourceItem: JsonItem) : IrisJsonResponse(sourceItem), GetChatResponse {
	override val result: Chat? by lazyItemOrNull("result") { IrisJsonChat(it) }
}