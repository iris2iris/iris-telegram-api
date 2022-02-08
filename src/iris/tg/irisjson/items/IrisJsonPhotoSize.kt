package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.PhotoSize

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonPhotoSize(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), PhotoSize {
	override val fileId: String
		get() = source["file_id"].asString()

	override val fileUniqueId: String
		get() = source["file_unique_id"].asString()

	override val width: Int
		get() = source["width"].asInt()

	override val height: Int
		get() = source["height"].asInt()

	override val fileSize: Int
		get() = source["file_size"].asIntOrNull() ?: 0
}