package iris.tg.examples

import iris.tg.api.items.Message
import iris.tg.command.CommandMatcher
import iris.tg.longpoll.TgLongPoll
import iris.tg.command.TgCommandPackHandler
import iris.tg.processors.pack.TgEventMessagePackHandlerAdapterBasicTypes
import iris.tg.processors.pack.TgTextPackHandler
import iris.tg.tgApi
import iris.tg.tgApiFuture
import kotlin.system.exitProcess

/**
 * @created 27.10.2020
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

	// Определяем обработчик команд
	val commandsHandler = TgCommandPackHandler<Message>()

	commandsHandler.text("кинг") {
		api.sendMessage(it.chat.id, "КОНГ!")
	}

	commandsHandler.text("мой ид") {
		val from = it.from!!
		api.sendMessage(it.chat.id, "ID ${from.firstName} ${from.lastName} (@${from.username}) равен: ${it.from!!.id}")
	}

	commandsHandler.regex("рандом (\\d+) (\\d+)") { message, params ->

		var first = params[1].toInt()
		var second = params[2].toInt()
		if (second < first)
			first = second.also { second = first }

		api.sendMessage(message.chat.id, "🎲 Случайное значение в диапазоне [$first..$second] выпало на ${(first..second).random()}")
	}

	// Определяем произвольно анализирующий текст команды обработчик команды
	commandsHandler += object : CommandMatcher {
		override fun testAndExecute(command: String, message: Message): Boolean {
			if (!(command.startsWith("пинг") || command.endsWith("пыньк"))) return false
			api.sendMessage(message.chat.id, "ПОНГ!")
			return true
		}
	}

	// Передаём в параметрах слушателя событий токен и созданный обработчик команд
	val listener = TgLongPoll(tgApi(token), TextHandlerAdapter(commandsHandler))
	listener.startPolling() // Можно запустить неблокирующего слушателя
	listener.join() // Даст дождаться завершения работы слушателя
	//listener.run() // Можно заблокировать дальнейшую работу потока, пока не будет остановлено

	exitProcess(0)
}

class TextHandlerAdapter<M: Message>(private val textHandler: TgTextPackHandler<M>) : TgEventMessagePackHandlerAdapterBasicTypes() {
	override fun texts(messages: List<Message>) {
		textHandler.texts(messages as List<M>)
	}
}