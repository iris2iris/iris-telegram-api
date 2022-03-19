package iris.tg.test

import iris.tg.api.JsonifierSimple
import iris.tg.keyboard.inlineKeyboardMarkup
import iris.tg.pojo.items.InlineKeyboardMarkup_Pojo
import iris.tg.pojo.items.ReplyKeyboardRemove_Pojo

/**
 * @created 07.02.2022
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */

val simpleJsonifier = JsonifierSimple()

fun main() {

	/*run {
		val arr = arrayOf("event1", "event2", "callback", "message")
		simpleJsonifier.array2JsonString(arr).apply(::println)
	}

	run {
		val arr = arrayOf<String>()
		simpleJsonifier.array2JsonString(arr).apply(::println)
	}*/

	/*run {
		val arr = listOf(
			MessageEntity_Pojo("type1", 3, 10, """https://url.ru/tada""", null, "RU"),
			MessageEntity_Pojo("type2", 23, 14, null, null, "US"),
		)
		simpleJsonifier.entities(arr).apply(::println)
	}

	run {
		simpleJsonifier.entities(listOf()).apply(::println)
	}*/

	/*run {
		val kb = inlineKeyboardMarkup {

			// Хочешь аргументами в функцию
			row(
				button("Wow 11"),
				button("Wow 12"),
			)

			// Хочешь DSL конструкторами накидывай
			row {
				button("Wow 21")
				button("Wow 22")
			}

			// Лучше, конечно, в функцию, ибо нет накладок с промежуточным билдером
		}

		simpleJsonifier.replyMarkup(kb).apply(::println)
	}

	run {
		val arr = InlineKeyboardMarkup_Pojo(listOf())
		simpleJsonifier.replyMarkup(arr).apply(::println)
	}*/

	run {
		val arr = ReplyKeyboardRemove_Pojo(true)
		simpleJsonifier.replyMarkup(arr)
			.apply(::println)
	}

}



