package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Document {
	/** Identifier for this file, which can be used to download or reuse the file */
	val file_id: String

	/** Unique identifier for this file, which is supposed to be the same over time and for different bots. Can't be used to download or reuse the file. */
	val file_unique_id: String

	/** Optional. Document thumbnail as defined by sender */
	val thumb: PhotoSize?

	/** Optional. Original filename as defined by sender */
	val file_name: String?

	/** Optional. MIME type of the file as defined by sender */
	val mime_type: String?

	/** Optional. File size in bytes */
	val file_size: Int
}