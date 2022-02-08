package iris.tg.examples

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

	val bot = Bot(token)

	bot.on.message {
		it.answer("Привет, @${it.from!!.username}. Как дела?")
	}

	bot.run_forever()
}