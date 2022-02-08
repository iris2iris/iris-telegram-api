package iris.tg.irisjson.response

import iris.json.JsonItem
import iris.tg.api.items.ChatMemberAdministrator
import iris.tg.api.response.GetChatAdministratorsResponse
import iris.tg.irisjson.items.IrisJsonChatMemberAdministrator

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonGetChatAdministratorsResponse(source: JsonItem) : IrisJsonResponse(source), GetChatAdministratorsResponse {
	override val result: List<ChatMemberAdministrator>? by lazyItemOrNull("result") { it.iterable().map { IrisJsonChatMemberAdministrator(it) } }
}