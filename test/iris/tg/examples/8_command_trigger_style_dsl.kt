package iris.tg.examples

import iris.tg.api.items.Message
import iris.tg.longpoll.TgLongPoll
import iris.tg.trigger.TgEventTriggerHandler
import iris.tg.command.TgCommandPackHandler
import iris.tg.tgApi
import iris.tg.tgApiFuture
import kotlin.system.exitProcess

/**
 * @created 01.11.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {

	// Инициализируем тестовые данные. В рабочих проектах этого делать не нужно
	TestUtil.init()
	val props = TestUtil.properties
	val token = props.getProperty("bot.token")

	// Создаём класс для отправки сообщений
	val api = tgApiFuture(token)
	// api.deleteWebhook().get() // Раскомментировать в случае ошибки конфликта работающего webhook'а

	// Определяем обработчик триггеров
	val triggerHandler = TgEventTriggerHandler<Message> {

		onMessage {
			println("Получено сообщение от ${it.chat.id}: ${it.text}")
		}

		onMessageEdit {
			println("Сообщение исправлено ${it.messageId}: ${it.text}")
		}

		onMessage(
			TgCommandPackHandler {
				text("пинг") {
					api.sendMessage(it.chat.id, "ПОНГ!")
				}
				text("мой ид") {
					api.sendMessage(it.chat.id, "Ваш ID равен: ${it.from!!.id}")
				}
				regex("""рандом (\d+) (\d+)""") { message, params ->

					var first = params[1].toInt()
					var second = params[2].toInt()
					if (second < first)
						first = second.also { second = first }

					api.sendMessage(message.chat.id, "🎲 Случайное значение в диапазоне [$first..$second] выпало на ${(first..second).random()}")
				}
			}
		)
	}

	// Передаём в параметрах слушателя событий токен и созданный обработчик команд
	val listener = TgLongPoll(tgApi(token), triggerHandler)
	listener.startPolling() // Можно запустить неблокирующего слушателя
	listener.join() // Даст дождаться завершения работы слушателя
	//listener.run() // Можно заблокировать дальнейшую работу потока, пока не будет остановлено

	exitProcess(0)
}