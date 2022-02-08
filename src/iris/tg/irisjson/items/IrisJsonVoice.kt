package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.Voice

open class IrisJsonVoice(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Voice {
	override val file_id: String
		get() = source["file_id"].asString()
	override val file_unique_id: String
		get() = source["file_unique_id"].asString()
	override val duration: Int
		get() = source["duration"].asInt()
	override val mime_type: String?
		get() = source["mime_type"].asStringOrNull()
	override val file_size: Int
		get() = source["file_size"].asIntOrNull() ?: 0
}
