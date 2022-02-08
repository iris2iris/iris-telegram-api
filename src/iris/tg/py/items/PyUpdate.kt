package iris.tg.py.items

import iris.json.JsonItem
import iris.tg.api.TgApiObjFuture
import iris.tg.api.items.Update
import iris.tg.irisjson.items.IrisJsonUpdate

class PyUpdate(private val api: TgApiObjFuture, source: JsonItem) : Update by IrisJsonUpdate(source) {
	override val message: PyMessage? by lazy { with(source["message"]) { if (isNull()) null else PyMessage(api, this) } }
	override val callbackQuery: PyCallbackQuery? by lazy { with(source["callback_query"]) { if (isNull()) null else PyCallbackQuery(api, this)} }
}