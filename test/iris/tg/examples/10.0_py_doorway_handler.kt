package iris.tg.examples

import iris.tg.api.TgApiObject
import iris.tg.longpoll.TgLongPoll
import iris.tg.py.items.PyCallbackQuery
import iris.tg.py.PySingleHandlerConverter
import iris.tg.py.PyResponseHandler
import iris.tg.py.PySingleHandler
import iris.tg.py.PySingleHandlerAdapter
import iris.tg.py.items.PyMessage

/**
 * @created 07.02.2022
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {

	// Инициализируем тестовые данные. В рабочих проектах этого делать не нужно
	TestUtil.init()
	val properties = TestUtil.properties
	val token = properties.getProperty("bot.token")

	val handler = object : PySingleHandlerAdapter() {

		override fun text(message: PyMessage) {
			message.answer("Ну вот лови обратку")
		}

		override fun callbackQuery(item: PyCallbackQuery) {
			item.answer("Да-да. Окей")
		}
	}

	val api = TgApiObject(token, PyResponseHandler())
	val lp = TgLongPoll(api, PySingleHandlerConverter(handler))
	lp.run()
}

