package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface User {
	val id: Long
	val isBot: Boolean
	val firstName:	String
	val lastName: String?
	val username: String?
	val languageCode: String?
	val canJoinGroups: Boolean
	val canReadAllGroupMessages: Boolean
	val supportsInlineQueries: Boolean
}