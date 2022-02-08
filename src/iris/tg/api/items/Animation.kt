package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Animation {
	val file_id: String
	val file_unique_id: String
	/** Video width as defined by sender */
	val width: Int
	/** Video height as defined by sender */
	val height: Int
	/** Duration of the video in seconds as defined by sender */
	val duration: Int
	/** Optional. Animation thumbnail as defined by sender */
	val thumb: PhotoSize?
	/** Optional. Original animation filename as defined by sender */
	val file_name: String?
	/** Optional. MIME type of the file as defined by sender */
	val mime_type: String?
	/** Optional. File size in bytes */
	val file_size: Int
}