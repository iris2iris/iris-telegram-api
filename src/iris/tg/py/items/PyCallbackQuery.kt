package iris.tg.py.items

import iris.json.JsonItem
import iris.tg.api.items.CallbackQuery
import iris.tg.irisjson.items.IrisJsonCallbackQuery
import iris.tg.py.Bot

class PyCallbackQuery(bot: Bot?, source: JsonItem) : PyItem(bot), CallbackQuery by IrisJsonCallbackQuery(source) {
	override val fromChatId: Long
		get() = message!!.chat.id
}