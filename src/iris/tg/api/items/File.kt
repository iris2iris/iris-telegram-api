package iris.tg.api.items

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface File {
	val fileId: String
	val fileUniqueId: String
	val fileSize: Int
	val filePath: String?
}