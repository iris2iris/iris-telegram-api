package iris.tg.pojo.items

import iris.tg.api.items.ReplyKeyboardRemove

/**
 * @created 21.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class ReplyKeyboardRemove_Pojo(
	override val selective: Boolean
) : ReplyKeyboardRemove {
	override val remove_keyboard = true
}