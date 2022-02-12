package iris.tg.py.items

import iris.json.JsonItem
import iris.tg.api.items.Message
import iris.tg.api.items.Update
import iris.tg.irisjson.items.IrisJsonUpdate
import iris.tg.py.Bot

class PyUpdate(bot: Bot?, source: JsonItem) : Update by IrisJsonUpdate(source) {
	override val message: PyMessage? by lazy { with(source["message"]) { if (isNull()) null else PyMessage(bot, this) } }
	override val editedMessage: Message? by lazy { with(source["edited_message"]) { if (isNull()) null else PyMessage(bot, this) } }
	override val callbackQuery: PyCallbackQuery? by lazy { with(source["callback_query"]) { if (isNull()) null else PyCallbackQuery(bot, this)} }
}