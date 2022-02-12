package iris.tg.py.items

import iris.json.JsonItem
import iris.tg.api.items.Message
import iris.tg.irisjson.items.IrisJsonMessage
import iris.tg.py.Bot

class PyMessage(bot: Bot?, source: JsonItem) : PyItem(bot), Message by IrisJsonMessage(source) {
	override val fromChatId: Long
		get() = chat.id
}