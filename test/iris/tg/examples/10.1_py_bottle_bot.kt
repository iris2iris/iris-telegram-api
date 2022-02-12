package iris.tg.examples

import iris.tg.api.AllowedUpdates
import iris.tg.py.Bot

/**
 * @created 08.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
fun main() {

	// Инициализируем тестовые данные. В рабочих проектах этого делать не нужно
	TestUtil.init()
	val properties = TestUtil.properties
	val token = properties.getProperty("bot.token")
	val toId = properties.getProperty("userTo.id").toLong()

	val bot = Bot(token, dropPending = true, allowedUpdates = AllowedUpdates.All)

	bot.defaultSendSettings.parseMode = "HTML"
	bot.defaultSendSettings.disableWebPagePreview = true

	bot.on.message {
		it.answer("Привет, <b>@${it.from!!.username}</b>. <u>Как дела</u>?")
	}

	bot.on.messages {
		it.forEach {
			it.answer("А это я ещё и рассылаю приветствие, <b>@${it.from!!.username}</b>. <u>Как дела</u>?")
		}
	}

	bot.on.messageEdit {
		it.answer("Не трогай редач")
	}

	bot.commands {

		text("привет") {
			it.answer("Привет, <b>${it.from!!.firstName}</b>!")
		}

		text("пинг", "кинг") {
			it.answer(
				when (it.text?.lowercase()) {
					"пинг" -> "ПОНГ!"
					"кинг" -> "КОНГ!"
					else -> "Не понял"
				}
			)
		}

	}

	bot.filter.onMessage {
		// отфильтруем только те сообщения, которые предназначены вашему аккаунту [toId]
		it.from!!.id == toId
	}

	bot.run_forever()
}