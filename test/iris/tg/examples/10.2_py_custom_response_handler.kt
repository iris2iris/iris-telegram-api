package iris.tg.examples

import iris.tg.api.AllowedUpdates
import iris.tg.irisjson.response.IrisJsonResponse
import iris.tg.py.Bot
import iris.tg.py.PyResponseHandler

/**
 * @created 12.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
fun main() {
	// Инициализируем тестовые данные. В рабочих проектах этого делать не нужно
	TestUtil.init()
	val properties = TestUtil.properties
	val token = properties.getProperty("bot.token")
	val responseHandler = MyPyResponseHandler()

	val bot = Bot(token, responseHandler = responseHandler)
	responseHandler.bot = bot

	// Вызовем ошибку
	val res = bot.api.sendMessage(1, "Вызовем ошибку, отправив в несуществующий чат").get()
	if (res.ok)
		println("Каким-то чудом ошибки не произошло: $res")
}

class MyPyResponseHandler : PyResponseHandler() {
	override fun process(method: String, data: String?): IrisJsonResponse {
		val res = super.process(method, data)
		if (!res.ok) {
			System.err.println("TG API Error: " + with(res.error) { "$description ($errorCode)" })
			Thread.currentThread().stackTrace
				.joinToString("\n")
				.apply(System.err::println)
		}
		return res
	}
}