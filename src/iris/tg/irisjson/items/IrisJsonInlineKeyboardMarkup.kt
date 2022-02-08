package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.CallbackGame
import iris.tg.api.items.InlineKeyboardButton
import iris.tg.api.items.InlineKeyboardMarkup
import iris.tg.api.items.LoginUrl

open class IrisJsonInlineKeyboardMarkup(it: JsonItem): IrisJsonTgItem(it), InlineKeyboardMarkup {
	override val inline_keyboard: List<List<InlineKeyboardButton>>
		by lazyItem { source["inline_keyboard"].iterable().map {
			it.iterable().map { IrisJsonInlineKeyboardButton(it) }
		} }
}

open class IrisJsonInlineKeyboardButton(it: JsonItem) : IrisJsonTgItem(it), InlineKeyboardButton {

	override val text: String by lazyItem { source["text"].asString() }

	override val url: String? by lazyItem { source["url"].asStringOrNull() }

	override val login_url: LoginUrl?
		get() = TODO("Not yet implemented")

	override val callback_data: String?
		get() = source["callback_data"].asStringOrNull()

	override val switch_inline_query: String?
		get() = source["switch_inline_query"].asStringOrNull()

	override val callback_game: CallbackGame?
		get() = TODO("Not yet implemented")

	override val pay: Boolean
		get() = source["pay"].asBooleanOrNull() ?: false
}