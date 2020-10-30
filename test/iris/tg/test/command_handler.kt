package iris.tg.test

import iris.tg.TgBotLongPoll
import iris.tg.api.TgApiFuture
import iris.tg.command.RegexCommand
import iris.tg.command.SimpleCommand
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

	commandsHandler += SimpleCommand("пинг") {
		api.sendMessage(it.peerId, "ПОНГ!")
	}

	commandsHandler += SimpleCommand("мой ид") {
		api.sendMessage(it.peerId, "Ваш ID равен: ${it.fromId}")
	}

	commandsHandler += "р" to RegexCommand("рандом (\\d+) (\\d+)") { vkMessage, params ->

		var first = params[1].toInt()
		var second = params[2].toInt()
		if (second < first)
			first = second.also { second = first }

		api.sendMessage(vkMessage.peerId, "🎲 Случайное значение в диапазоне [$first..$second] выпало на ${(first..second).random()}")
	}

	// Передаём в параметрах слушателя событий токен и созданный обработчик команд
	val listener = TgBotLongPoll(token, commandsHandler)
	listener.run() // блокирует дальнейшее продвижение, пока не будет остановлено

	exitProcess(0)
}