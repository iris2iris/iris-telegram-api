package iris.tg.keyboard.callback

import iris.tg.api.TgApiAbstract
import iris.tg.api.items.*
import iris.tg.pojo.items.InlineKeyboardButton_Pojo
import iris.tg.pojo.items.InlineKeyboardMarkup_Pojo
import iris.tg.processors.single.TgEventMessageSingleHandlerAdapter
import iris.tg.processors.single.TgEventMessageSingleHandlerAdapterBasicTypes

/**
 * @created 07.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */

class CallbackMonster(private val api: TgApiAbstract<*>) : TgEventMessageSingleHandlerAdapterBasicTypes() {

	override fun callbackQuery(item: CallbackQuery) {
		api.answerCallbackQuery(item.id)
		val resp = keys[item.data] ?: return
		resp(item)
	}

	private val keys: MutableMap<String, (CallbackQuery) -> Unit> = mutableMapOf()
	private val functions: MutableMap<(CallbackQuery) -> Unit, String> = mutableMapOf()


	fun button(text: String, action: (CallbackQuery) -> Unit): InlineKeyboardButton {
		val resp = functions.getOrPut(action) {
			action.hashCode().toString(16)
				.also { keys[it] = action }
		}
		return InlineKeyboardButton_Pojo(text, callback_data = resp)
	}

	fun keyboard(initializer: CallbackMonsterBuilder.() -> Unit): InlineKeyboardMarkup {
		return CallbackMonsterBuilder(this).apply(initializer).build()
	}

	fun singleButtonKeyboard(text: String, action: (CallbackQuery) -> Unit): InlineKeyboardMarkup {
		return InlineKeyboardMarkup_Pojo(listOf(listOf(button(text, action))))
	}
}

class CallbackMonsterBuilder(private val handler: CallbackMonster) {

	private val rows: MutableList<List<InlineKeyboardButton>> = mutableListOf()

	fun build() : InlineKeyboardMarkup = InlineKeyboardMarkup_Pojo(rows)

	fun button(text: String, action: (CallbackQuery) -> Unit): InlineKeyboardButton {
		return handler.button(text, action)
	}

	inner class RowBuilder {

		private val row = mutableListOf<InlineKeyboardButton>()

		fun build(): List<InlineKeyboardButton> {
			return row
		}

		fun button(text: String, action: (CallbackQuery) -> Unit) {
			row += handler.button(text, action)
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

	fun row(vararg items: InlineKeyboardButton) {
		rows += items.toList()
	}
}

/*
fun callbackMonsterInlineKeyboardMarkup(handler: CallbackMonster, initializer: CallbackMonsterBuilder.() -> Unit): InlineKeyboardMarkup {
	return CallbackMonsterBuilder(handler).apply(initializer).build()
}

fun singleButtonCallbackMonsterInlineKeyboardMarkup(handler: CallbackMonster, text: String, action: (CallbackQuery) -> Unit): InlineKeyboardMarkup {
	return InlineKeyboardMarkup_Pojo(listOf(listOf(handler.button(text, action))))
}*/
