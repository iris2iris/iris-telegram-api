package iris.tg.pojo.items

import iris.tg.api.items.InlineKeyboardButton
import iris.tg.api.items.InlineKeyboardMarkup

/**
 * @created 07.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class InlineKeyboardMarkup_Pojo(
	override val inline_keyboard: List<List<InlineKeyboardButton>>
) : InlineKeyboardMarkup