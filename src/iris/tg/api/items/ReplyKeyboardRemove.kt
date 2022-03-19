package iris.tg.api.items

/**
 * @created 07.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface ReplyKeyboardRemove : ReplyMarkup {
	val remove_keyboard: Boolean
	val selective: Boolean
}