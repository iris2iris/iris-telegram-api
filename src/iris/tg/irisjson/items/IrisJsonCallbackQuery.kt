package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.CallbackQuery
import iris.tg.api.items.Message
import iris.tg.api.items.User

/**
 * @created 07.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonCallbackQuery(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), CallbackQuery {
	override val id: String by lazyItem { source["id"].asString() }

	override val from: User by lazyItem { IrisJsonUser(source["from"]) }

	override val message: Message? by lazyItemOrNull("message") { IrisJsonMessage(it) }

	override val inlineMessageId: String?
		get() = source["inline_message_id"].asStringOrNull()
	override val chatInstance: String
		get() = source["chat_instance"].asString()
	override val data: String?
		get() = source["data"].asStringOrNull()
	override val gameShortName: String?
		get() = source["game_short_name"].asStringOrNull()
}