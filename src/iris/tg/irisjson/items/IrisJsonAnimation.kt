package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.Animation
import iris.tg.api.items.PhotoSize

open class IrisJsonAnimation(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Animation {
	override val file_id: String
		get() = source["file_id"].asString()
	override val file_unique_id: String
		get() = source["file_unique_id"].asString()
	override val width: Int
		get() = source["width"].asInt()
	override val height: Int
		get() = source["height"].asInt()
	override val duration: Int
		get() = source["duration"].asInt()
	override val thumb: PhotoSize?
		get() = itemOrNull(source["thumb"]) { IrisJsonPhotoSize(it) }
	override val file_name: String?
		get() = source["file_name"].asStringOrNull()
	override val mime_type: String?
		get() = source["mime_type"].asStringOrNull()
	override val file_size: Int
		get() = source["file_size"].asIntOrNull() ?: 0
}