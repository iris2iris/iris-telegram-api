package iris.tg.keyboard

import iris.tg.api.items.CallbackGame
import iris.tg.api.items.InlineKeyboardButton
import iris.tg.api.items.InlineKeyboardMarkup
import iris.tg.api.items.LoginUrl
import iris.tg.pojo.items.InlineKeyboardButton_Pojo
import iris.tg.pojo.items.InlineKeyboardMarkup_Pojo

/**
 * @created 07.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class InlineKeyboardMarkupBuilder {

	private val rows: MutableList<List<InlineKeyboardButton>> = mutableListOf()

	fun build() : InlineKeyboardMarkup = InlineKeyboardMarkup_Pojo(rows)

	/*operator fun plusAssign(row: List<InlineKeyboardButton>) {
		rows += row
	}*/

	fun row(items: List<InlineKeyboardButton>) {
		rows += items
	}
	fun row(vararg items: InlineKeyboardButton) {
		row(items.toList())
	}

	class RowBuilder {

		private val row = mutableListOf<InlineKeyboardButton>()

		fun build(): List<InlineKeyboardButton> {
			return row
		}

		fun button(text: String, url: String? = null,
				   login_url: LoginUrl? = null,
				   callback_data: String? = null,
				   switch_inline_query: String? = null,
				   callback_game: CallbackGame? = null,
				   pay: Boolean = false
		) {
			row += InlineKeyboardButton_Pojo(text, url, login_url, callback_data, switch_inline_query, callback_game, pay)
		}

		fun button(item: InlineKeyboardButton) {
			row += item
		}

		operator fun plusAssign(button: InlineKeyboardButton) {
			row += button
		}
	}

	fun row(initializer: RowBuilder.() -> Unit) {
		rows += RowBuilder().apply(initializer).build()
	}


	fun button(text: String, url: String? = null,
			   login_url: LoginUrl? = null,
			   callback_data: String? = null,
			   switch_inline_query: String? = null,
			   callback_game: CallbackGame? = null,
			   pay: Boolean = false
	): InlineKeyboardButton {
		return InlineKeyboardButton_Pojo(text, url, login_url, callback_data, switch_inline_query, callback_game, pay)
	}

	fun single(button: InlineKeyboardButton) {
		rows.clear()
		rows += listOf(button)
	}
}

fun inlineKeyboardMarkup(items: List<List<InlineKeyboardButton>>): InlineKeyboardMarkup {
	return InlineKeyboardMarkup_Pojo(items)
}

fun inlineKeyboardMarkup(initializer: InlineKeyboardMarkupBuilder.() -> Unit): InlineKeyboardMarkup {
	return InlineKeyboardMarkupBuilder().apply(initializer).build()
}

fun singleButtonKeyboardMarkup(text: String, url: String? = null,
	login_url: LoginUrl? = null,
	callback_data: String? = null,
	switch_inline_query: String? = null,
	callback_game: CallbackGame? = null,
	pay: Boolean = false
) = singleButtonKeyboardMarkup(InlineKeyboardButton_Pojo(text, url, login_url, callback_data, switch_inline_query, callback_game, pay))

fun singleButtonKeyboardMarkup(button: InlineKeyboardButton): InlineKeyboardMarkup {
	return InlineKeyboardMarkup_Pojo(listOf(listOf(button)))
}