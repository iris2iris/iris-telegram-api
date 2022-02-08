package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface MessageEntity : TgItem {
	val type: String
	val offset: Int
	val length: Int
	val url: String?
	val user: User?
	val language: String?
}