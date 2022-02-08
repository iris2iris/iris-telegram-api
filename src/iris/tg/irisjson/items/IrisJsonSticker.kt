package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.MaskPosition
import iris.tg.api.items.PhotoSize
import iris.tg.api.items.Sticker

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonSticker(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Sticker {
	override val file_id: String
		get() = source["file_id"].asString()
	override val file_unique_id: String
		get() = source["file_unique_id"].asString()
	override val width: Int
		get() = source["width"].asInt()
	override val height: Int
		get() = source["height"].asInt()
	override val is_animated: Boolean
		get() = source["is_animated"].asBooleanOrNull() ?: false
	override val is_video: Boolean
		get() = source["is_video"].asBooleanOrNull() ?: false

	override val thumb: PhotoSize?
		get() = itemOrNull(source["thumb"]) { IrisJsonPhotoSize(it) }

	override val emoji: String?
		get() = source["emoji"].asStringOrNull()
	override val set_name: String?
		get() = source["set_name"].asStringOrNull()

	override val mask_position: MaskPosition?
		get() = TODO("Not yet implemented")

	override val file_size: Int
		get() = TODO("Not yet implemented")
}