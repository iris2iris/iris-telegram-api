package iris.tg.command

import iris.tg.api.items.Message


/**
 * @created 27.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class CommandExtractorDefault(private val prefixes: String?) : CommandExtractor {

	override fun extractCommand(message: Message): String? {
		val text = message.text ?: return null
		if (text.isEmpty()) return null

		val offset = if (prefixes != null) {
			val first = text.first()
			if (!prefixes.contains(first))
				return null
			1
		} else
			0


		val ind = text.indexOf('\n')
		return when {
			ind == -1 -> if (text.length <= 150) correctText(text, offset) else null
			ind <= 150 -> text.substring(offset, ind)
			else -> null
		}?.lowercase()
	}

	private fun correctText(text: String, startIndex: Int): String {
		return if (startIndex == 0) text else text.substring(startIndex)
	}
}