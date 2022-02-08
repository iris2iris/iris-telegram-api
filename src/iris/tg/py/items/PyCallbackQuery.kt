package iris.tg.py.items

import iris.json.JsonItem
import iris.tg.api.TgApiObjFuture
import iris.tg.api.items.CallbackQuery
import iris.tg.irisjson.items.IrisJsonCallbackQuery

class PyCallbackQuery(api: TgApiObjFuture, source: JsonItem) : PyItem(api), CallbackQuery by IrisJsonCallbackQuery(source) {
	override val fromChatId: Long
		get() = message!!.chat.id
}