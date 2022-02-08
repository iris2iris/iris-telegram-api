package iris.tg.py.items

import iris.json.JsonItem
import iris.tg.api.TgApiObjFuture
import iris.tg.api.items.Message
import iris.tg.irisjson.items.IrisJsonMessage

class PyMessage(api: TgApiObjFuture, source: JsonItem) : PyItem(api), Message by IrisJsonMessage(source) {
	override val fromChatId: Long
		get() = chat.id
}