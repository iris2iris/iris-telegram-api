package iris.tg.test

import iris.tg.TgBotLongPoll
import iris.tg.TgEventHandlerAdapter
import iris.tg.api.TgApiFuture
import iris.tg.event.CallbackEvent
import iris.tg.event.Message
import kotlin.system.exitProcess

/**
 * @created 27.09.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */

fun main() {
	TestUtil.init()
	val props = TestUtil.getProperties()
	val token = props.getProperty("group.token")

	// Создаём класс для отправки сообщений
	val api = TgApiFuture(token)

	// Определяем обработчик событий
	val simpleMessageHandler = object : TgEventHandlerAdapter() {

		override fun processMessage(message: Message) {
			val text = message.text
			println("Получено сообщение: $text")

			if (text =="пинг") {
				println("Команда пинг получена")

				// Шлём ответ
				api.sendMessage(message.peerId, "ПОНГ")
			}
		}

		override fun processCallbacks(callbacks: List<CallbackEvent>) {
			for (callback in callbacks) {
				println("Получено callback-событие: ${callback.id} payload=${callback.data}")
			}
		}
	}

	// Передаём в параметрах слушателя событий токен и созданный обработчик событий
	val listener = TgBotLongPoll(token, simpleMessageHandler)
	listener.run() // блокирует дальнейшее продвижение, пока не будет остановлено

	exitProcess(0)
}