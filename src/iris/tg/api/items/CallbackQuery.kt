package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface CallbackQuery {
	val id: String
	val from: User
	val message: Message?
	val inlineMessageId: String?
	val chatInstance: String
	val data: String?
	val gameShortName: String?
}