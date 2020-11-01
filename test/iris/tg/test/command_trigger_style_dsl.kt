package iris.tg.test

import iris.tg.TgBotLongPoll
import iris.tg.TgTriggerEventHandler
import iris.tg.api.TgApiFuture
import iris.tg.command.TgCommandHandler
import iris.tg.command.commands
import kotlin.system.exitProcess

/**
 * @created 01.11.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {
	TestUtil.init()
	val props = TestUtil.getProperties()
	val token = props.getProperty("group.token")

	// Создаём класс для отправки сообщений
	val api = TgApiFuture(token)


	// Определяем обработчик триггеров
	val triggerHandler = TgTriggerEventHandler {

		onMessage {
			for (message in it)
				println("Получено сообщение от ${message.peerId}: ${message.text}")
		}

		onMessageEdit {
			for (message in it)
				println("Сообщение исправлено ${message.id}: ${message.text}")
		}

		onMessage(
			TgCommandHandler().addAll(
				commands {
					"пинг" to {
						api.sendMessage(it.peerId, "ПОНГ!")
					}
					"мой ид" to {
						api.sendMessage(it.peerId, "Ваш ID равен: ${it.fromId}")
					}
					regex("""рандом (\d+) (\d+)""") to { vkMessage, params ->

						var first = params[1].toInt()
						var second = params[2].toInt()
						if (second < first)
							first = second.also { second = first }

						api.sendMessage(vkMessage.peerId, "🎲 Случайное значение в диапазоне [$first..$second] выпало на ${(first..second).random()}")
					}
				}
		))
	}

	// Передаём в параметрах слушателя событий токен и созданный обработчик команд
	val listener = TgBotLongPoll(token, triggerHandler)
	listener.run() // блокирует дальнейшее продвижение, пока не будет остановлено

	exitProcess(0)
}