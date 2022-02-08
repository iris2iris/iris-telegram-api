package iris.tg.irisjson.response

import iris.json.JsonItem
import iris.tg.api.items.ChatMember
import iris.tg.api.items.IrisJsonChatMemberFactory
import iris.tg.api.response.GetChatMemberResponse

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonGetChatMemberResponse(sourceItem: JsonItem) : IrisJsonResponse(sourceItem), GetChatMemberResponse {
	override val result: ChatMember? by lazyItemOrNull("result") { IrisJsonChatMemberFactory.create(it) }
}