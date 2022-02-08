package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface PhotoSize {
	val fileId: String
	val fileUniqueId: String
	val width: Int
	val height: Int
	val fileSize: Int
}