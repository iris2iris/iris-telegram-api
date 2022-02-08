package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.VoiceChatScheduled

open class IrisJsonVoiceChatScheduled(it: JsonItem): IrisJsonTgItem(it), VoiceChatScheduled {
	override val start_date: Int
		get() = source["start_date"].asInt()
}
