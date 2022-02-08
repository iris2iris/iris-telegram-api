package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.MessageAutoDeleteTimerChanged

open class IrisJsonMessageAutoDeleteTimerChanged(it: JsonItem): IrisJsonTgItem(it), MessageAutoDeleteTimerChanged {
	override val message_auto_delete_time: Int
		get() = source["message_auto_delete_time"].asInt()
}
