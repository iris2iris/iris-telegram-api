package iris.tg.api

import iris.tg.api.items.*

/**
 * @created 07.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class JsonifierSimple: Jsonifier {
	override fun entities(entities: List<MessageEntity>): String {
		if (entities.isEmpty()) return "[]"
		val sb = StringBuilder("[")
		for (entity in entities) with(entity) {
			sb.append("{\"type\":\" ").append(type).append("\",")
			sb.append("\"offset\": ").append(offset).append(",")
			sb.append("\"length\": ").append(length).append(",")
			url?.apply { sb.append("\"url\":\"").append(this).append("\",") }
			language?.apply { sb.append("\"language\":\"").append(this).append("\",") }
			// User is not implemented
			sb[sb.lastIndex] = '}'
			sb.append(',')
		}
		sb[sb.lastIndex] = ']'
		return sb.toString()
	}

	override fun replyMarkup(replyMarkup: ReplyMarkup): String {
		return when (replyMarkup) {
			is InlineKeyboardMarkup -> inlineKeyboardMarkup(replyMarkup)
			else -> TODO("Not implemented")
		}
	}

	private fun inlineKeyboardMarkup(replyMarkup: InlineKeyboardMarkup) : String {
		if (replyMarkup.inline_keyboard.isEmpty()) return "{\"inline_keyboard\": []}"
		val sb = StringBuilder("{\"inline_keyboard\": [")
		for (row in replyMarkup.inline_keyboard) {
			if (row.isEmpty()) {
				sb.append("[],")
				continue
			}
			sb.append('[')
			for (col in row) with(col) {
				sb.append("{\"text\": \"").append(escapeString(text)).append("\",")
				url?.apply { sb.append("{\"url\": \"").append(escapeString(this)).append("\",") }
				// login_url not implemented

				callback_data?.apply { sb.append("\"callback_data\": \"").append(escapeString(this)).append("\",") }
				switch_inline_query?.apply { sb.append("\"switch_inline_query\": \"").append(escapeString(this)).append("\",") }

				// callback_game is not implemented
				if (pay)
					sb.append("\"switch_inline_query\": true,")
				sb[sb.lastIndex] = '}'
				sb.append(',')
			}
			sb[sb.lastIndex] = ']'
			sb.append(',')
		}
		sb[sb.lastIndex] = ']'
		sb.append('}')
		return sb.toString()
	}

	private fun escapeString(text: String): String {
		return text.replace("\"", "\\\"").replace("\\", "\\\\")
	}

	override fun array2JsonString(array: Array<String>): String {
		if (array.isEmpty()) return "[]"
		return array.joinToString("\", \"", "[\"", "\"]", transform = this::escapeString)
	}
}