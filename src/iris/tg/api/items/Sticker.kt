package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Sticker {
	val file_id: String
	val file_unique_id: String
	val width: Int
	val height: Int
	val is_animated: Boolean
	val is_video: Boolean
	val thumb: PhotoSize?
	val emoji: String?
	val set_name: String?
	val mask_position: MaskPosition?
	val file_size: Int
}