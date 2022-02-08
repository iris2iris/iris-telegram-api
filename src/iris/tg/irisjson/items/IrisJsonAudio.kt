package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.Audio
import iris.tg.api.items.PhotoSize

open class IrisJsonAudio(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Audio {
	override val file_id: String
		get() = source["file_id"].asString()
	override val file_unique_id: String
		get() = source["file_unique_id"].asString()
	override val duration: Int
		get() = source["duration"].asInt()
	override val performer: String?
		get() = source["performer"].asStringOrNull()
	override val title: String?
		get() = source["title"].asStringOrNull()
	override val file_name: String?
		get() = source["file_name"].asStringOrNull()
	override val mime_type: String?
		get() = source["mime_type"].asStringOrNull()
	override val file_size: Int
		get() = source["file_size"].asIntOrNull() ?: 0
	override val thumb: PhotoSize?
		get() = itemOrNull(source["thumb"]) { IrisJsonPhotoSize(it) }
}
