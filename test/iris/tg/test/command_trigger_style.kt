package iris.tg.test

import iris.tg.TgBotLongPoll
import iris.tg.TgTriggerEventHandler
import iris.tg.api.TgApiFuture
import iris.tg.command.CommandMatcherRegex
import iris.tg.command.CommandMatcherSimple
import iris.tg.command.TgCommandHandler
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
	val triggerHandler = TgTriggerEventHandler()

	// можно настраивать лямбдами
	triggerHandler.onMessage {
		for (message in it)
			println("Получено сообщение от ${message.peerId}: ${message.text}")
	}

	triggerHandler.onMessageEdit {
		for (message in it)
			println("Сообщение исправлено ${message.id}: ${message.text}")
	}

	// можно настраивать классами триггеров
	triggerHandler += TgCommandHandler(
		commands = listOf(
			CommandMatcherSimple("пинг") {
				api.sendMessage(it.peerId, "ПОНГ!")
			},

			CommandMatcherSimple("мой ид") {
				api.sendMessage(it.peerId, "Ваш ID равен: ${it.fromId}")
			},

			CommandMatcherRegex("""рандом (\d+) (\d+)""") { vkMessage, params ->

				var first = params[1].toInt()
				var second = params[2].toInt()
				if (second < first)
					first = second.also { second = first }

				api.sendMessage(vkMessage.peerId, "🎲 Случайное значение в диапазоне [$first..$second] выпало на ${(first..second).random()}")
			}
		)
	)

	// Передаём в параметрах слушателя событий токен и созданный обработчик команд
	val listener = TgBotLongPoll(token, triggerHandler)
	listener.startPolling() // Можно запустить неблокирующего слушателя
	listener.join() // Даст дождаться завершения работы слушателя
	//listener.run() // Можно заблокировать дальнейшую работу потока, пока не будет остановлено

	exitProcess(0)
}