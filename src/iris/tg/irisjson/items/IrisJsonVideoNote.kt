package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.PhotoSize
import iris.tg.api.items.VideoNote

open class IrisJsonVideoNote(sourceItem: JsonItem): IrisJsonTgItem(sourceItem), VideoNote {
	override val file_id: String
		get() = source["file_id"].asString()
	override val file_unique_id: String
		get() = source["file_unique_id"].asString()
	override val length: Int
		get() = source["length"].asInt()
	override val duration: Int
		get() = source["duration"].asInt()
	override val thumb: PhotoSize?
		get() = itemOrNull(source["thumb"]) { IrisJsonPhotoSize(it) }
	override val file_size: Int
		get() = source["file_size"].asIntOrNull() ?: 0
}
