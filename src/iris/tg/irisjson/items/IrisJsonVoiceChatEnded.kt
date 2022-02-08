package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.VoiceChatEnded

open class IrisJsonVoiceChatEnded(it: JsonItem): IrisJsonTgItem(it), VoiceChatEnded {
	override val duration: Int
		get() = source["duration"].asInt()
}
