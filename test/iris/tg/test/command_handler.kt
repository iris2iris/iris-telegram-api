package iris.tg.test

import iris.tg.TgBotLongPoll
import iris.tg.api.TgApiFuture
import iris.tg.command.CommandMatcherRegex
import iris.tg.command.CommandMatcherSimple
import iris.tg.command.TgCommandHandler
import kotlin.system.exitProcess

/**
 * @created 27.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {
	TestUtil.init()
	val props = TestUtil.getProperties()
	val token = props.getProperty("group.token")

	// Создаём класс для отправки сообщений
	val api = TgApiFuture(token)

	// Определяем обработчик команд
	val commandsHandler = TgCommandHandler()

	commandsHandler += CommandMatcherSimple("пинг") {
		api.sendMessage(it.peerId, "ПОНГ!")
	}

	commandsHandler += CommandMatcherSimple("мой ид") {
		api.sendMessage(it.peerId, "Ваш ID равен: ${it.fromId}")
	}

	commandsHandler += CommandMatcherRegex("рандом (\\d+) (\\d+)") { vkMessage, params ->

		var first = params[1].toInt()
		var second = params[2].toInt()
		if (second < first)
			first = second.also { second = first }

		api.sendMessage(vkMessage.peerId, "🎲 Случайное значение в диапазоне [$first..$second] выпало на ${(first..second).random()}")
	}

	// Передаём в параметрах слушателя событий токен и созданный обработчик команд
	val listener = TgBotLongPoll(token, commandsHandler)
	listener.startPolling() // Можно запустить неблокирующего слушателя
	listener.join() // Даст дождаться завершения работы слушателя
	//listener.run() // Можно заблокировать дальнейшую работу потока, пока не будет остановлено

	exitProcess(0)
}