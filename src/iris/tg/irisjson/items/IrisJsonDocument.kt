package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.Document
import iris.tg.api.items.PhotoSize

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonDocument(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Document {
	override val file_id: String
		get() = source["file_id"].asString()
	override val file_unique_id: String
		get() = source["file_unique_id"].asString()
	override val thumb: PhotoSize?
		get() = itemOrNull(source["thumb"]) { IrisJsonPhotoSize(it) }
	override val file_name: String?
		get() = source["file_name"].asStringOrNull()
	override val mime_type: String?
		get() = source["mime_type"].asStringOrNull()
	override val file_size: Int
		get() = source["file_size"].asIntOrNull() ?: 0
}