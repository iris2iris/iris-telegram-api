package iris.tg.api

import iris.tg.api.items.*

/**
 * @created 07.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class JsonifierSimple: Jsonifier {

	private fun entities(sb: StringBuilder, entities: List<MessageEntity>) {
		sb.append('[')
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
	}

	override fun entities(entities: List<MessageEntity>): String {
		if (entities.isEmpty()) return "[]"
		val sb = StringBuilder()
		entities(sb, entities)
		/*val sb = StringBuilder("[")
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
		sb[sb.lastIndex] = ']'*/
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

	override fun array2JsonString(array: List<String>): String {
		if (array.isEmpty()) return "[]"
		return array.joinToString("\", \"", "[\"", "\"]", transform = this::escapeString)
	}

	override fun chatPermissions(permissions: ChatPermissions): String {
		return with(permissions) {
			"""{"can_send_messages": $can_send_messages, "can_send_media_messages": $can_send_media_messages, "can_send_polls": $can_send_polls, "can_send_other_messages": $can_send_other_messages, "can_add_web_page_previews": $can_add_web_page_previews, "can_change_info": $can_change_info, "can_invite_users": $can_invite_users, "can_pin_messages": $can_pin_messages,}"""
		}
	}



	override fun inputMedia(inputMedia: List<InputMedia>): String {
		if (inputMedia.isEmpty()) return "[]"
		val sb = StringBuilder("[")
		for (i in inputMedia) {
			sb.append('{')
			when (i) {
				is InputMediaAnimation -> im(sb, i)
				is InputMediaVideo -> im(sb, i)
				is InputMediaDocument -> im(sb, i)
				is InputMediaPhoto -> im(sb, i)
				is InputMediaThumbable -> imTh(sb, i)
			}
			sb.append("},")
		}
		sb.setCharAt(sb.lastIndex, ']')
		return sb.toString()
	}

	private fun im(sb: StringBuilder, item: InputMediaDocument) {
		commonMedia(sb, item)
		if (item.disable_content_type_detection)
			sb.append(""", "disable_content_type_detection": true""")
	}

	private fun im(sb: StringBuilder, item: InputMediaPhoto) {
		commonMedia(sb, item)
	}

	private fun im(sb: StringBuilder, item: InputMediaAnimation) {
		imTh(sb, item)
		with(item) {
			if (width != 0)
				sb.append(""", "width": """).append(width)

			if (height != 0)
				sb.append(""", "height": """).append(height)

			if (duration != 0)
				sb.append(""", "duration": """).append(duration)
		}

	}

	private fun im(sb: StringBuilder, item: InputMediaVideo) {
		im(sb, item as InputMediaAnimation)


		item.supports_streaming?.apply {
			sb.append(""", "supports_streaming": """).append(this)
		}
	}

	private fun imTh(sb: StringBuilder, item: InputMediaThumbable) {
		commonMedia(sb, item)
		item.thumb?.apply {
			sb.append(""", "thumb": """").append(escapeString(this)).append('"')
		}
	}

	private fun commonMedia(sb: StringBuilder, item: InputMedia) {
		with(item) {
			sb.append(""""type": """").append(type)
			.append("""", "media": """").append(media).append('"')
			if (caption != null)
				sb.append(""", "caption": """").append(caption).append('"')
			if (parse_mode != null)
				sb.append(""", "parse_mode": """").append(parse_mode).append('"')
			if (!caption_entities.isNullOrEmpty()) {
				sb.append(""", "caption_entities": """)
				entities(sb, caption_entities!!)
			}
		}
	}
}