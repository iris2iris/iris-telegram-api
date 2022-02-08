package iris.tg.examples

import iris.tg.longpoll.TgLongPoll
import iris.tg.api.items.CallbackQuery
import iris.tg.api.items.Message
import iris.tg.processors.single.TgEventMessageSingleHandlerAdapter
import iris.tg.processors.single.TgEventMessageSingleHandlerAdapterBasicTypes
import iris.tg.tgApi
import iris.tg.tgApiFuture
import kotlin.system.exitProcess

/**
 * @created 27.09.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 * Пример запуска слушателя событий Телеграм методом long polling
 */

fun main() {


	// Инициализируем тестовые данные. В рабочих проектах этого делать не нужно
	TestUtil.init()
	val props = TestUtil.properties
	val token = props.getProperty("bot.token")

	// Создаём объект TgApi с неблокирующими запросами для отправки сообщений
	val api = tgApiFuture(token)

	// api.deleteWebhook().get() // Раскомментировать в случае ошибки конфликта работающего webhook'а

	// Определяем обработчик получаемых событий
	val simpleMessageHandler = object : TgEventMessageSingleHandlerAdapterBasicTypes() {

		override fun text(message: Message) {

			api.sendMessage(message.chat.id,
				message.text?.let { "Получено сообщение: $it" }
				?: message.sticker?.let { "Получен стикер: $it" }
				?: message.video?.let { "Получено видео: $it" }
				?: message.audio?.let { "Получено аудио: $it" }
				?: message.animation?.let { "Получен GIF: $it" }
				?: message.photo?.let { "Получено фото: $it" }
				?: "Получено сообщение: $message"
			)

			val text = message.text
			if (text =="пинг") {
				println("Команда Пинг получена")

				// Шлём ответ
				api.sendMessage(message.chat.id, "ПОНГ")
			}
		}

		override fun callbackQuery(callback: CallbackQuery) {
			println("Получено callback-событие: ${callback.id} data=${callback.data}")
		}
	}

	// Передаём в параметрах слушателя событий токен и созданный обработчик событий
	val listener = TgLongPoll(tgApi(token), simpleMessageHandler)
	listener.startPolling() // Можно запустить неблокирующего слушателя
	listener.join() // Даст дождаться завершения работы слушателя

	//listener.run() // А можно просто заблокировать дальнейшую работу потока, пока не будет остановлено

	exitProcess(0)
}