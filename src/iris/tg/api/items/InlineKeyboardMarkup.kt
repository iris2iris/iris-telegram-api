package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface InlineKeyboardMarkup : ReplyMarkup {
	/** Array of button rows, each represented by an Array of InlineKeyboardButton objects */
	val inline_keyboard: List<List<InlineKeyboardButton>>
}