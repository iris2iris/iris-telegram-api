package iris.tg.pojo.items

import iris.tg.api.items.CallbackGame
import iris.tg.api.items.InlineKeyboardButton
import iris.tg.api.items.LoginUrl

open class InlineKeyboardButton_Pojo(
	override val text: String,
	override val url: String? = null,
	override val login_url: LoginUrl? = null,
	override val callback_data: String? = null,
	override val switch_inline_query: String? = null,
	override val callback_game: CallbackGame? = null,
	override val pay: Boolean = false
) : InlineKeyboardButton

