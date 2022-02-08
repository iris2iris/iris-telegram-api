package iris.tg.examples

import iris.tg.api.items.Message
import iris.tg.command.Command
import iris.tg.longpoll.TgLongPoll
import iris.tg.command.TgCommandPackHandler
import iris.tg.tgApi
import iris.tg.tgApiFuture
import java.util.*
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
	val commandsHandler = TgCommandPackHandler<Message> {
		text("время") {
			api.sendMessage(it.chat.id, "Время сервера: ${Date()}!")
		}
	}

	// Конфигурирование команд в стиле DSL
	commandsHandler.commands {

		// пример набора синонимом для команды
		text("пинг", "кинг") {
			api.sendMessage(it.chat.id,
				when(it.text!!) {
					"пинг" -> "ПОНГ!"
					"кинг" -> "КОНГ!"
					else -> "Как я здесь очутился???"
				}
			)
		}

		// выдача информации по пользователю
		text("мой ид", "/me") {
			val from = it.from!!
			api.sendMessage(it.chat.id, "ID ${from.firstName} ${from.lastName} (@${from.username}) равен: ${from.id}")
		}

		// команды с регулярным выражениями
		regex("""рандом (\d+) (\d+)""") { message, params ->
			var first = params[1].toInt()
			var second = params[2].toInt()
			if (second < first)
				first = second.also { second = first }

			api.sendMessage(message.chat.id, "🎲 Случайное значение в диапазоне [$first..$second] выпало на ${(first..second).random()}")
		}

		// возможность прикрепить готовую функцию с подходящей сигнатурой (message: Message) -> Unit
		text(listOf("как дела?", "привет, как дела?"), ::answerHowAreYou)

		// возможность прикрепить готовый класс типа Command
		text(listOf("кто ты?", "кто ты"), AnswerWhoAreYou())

		regex("""кто я\??""", "(?:мой )?профиль") { message, params ->
			val from = message.from!!
			api.sendMessage(message.chat.id, "Вы ${from.firstName} ${from.lastName} [@${from.username}]. ID = ${from.id}")
		}
	}


	// Передаём в параметрах слушателя событий токен и созданный обработчик команд
	val listener = TgLongPoll(tgApi(token), TextHandlerAdapter(commandsHandler))
	listener.startPolling() // Можно запустить неблокирующего слушателя
	listener.join() // Даст дождаться завершения работы слушателя
	//listener.run() // Можно заблокировать дальнейшую работу потока, пока не будет остановлено

	exitProcess(0)
}

fun answerHowAreYou(message: Message) {
	TestUtil.api.sendMessage(message.chat.id, "Пока не родила!")
}

class AnswerWhoAreYou: Command {
	override fun run(message: Message) {
		TestUtil.api.sendMessage(message.chat.id, "Я есть бот")
	}
}

fun ttt() {
	// Определяем обработчик команд
	val commandsHandler = TgCommandPackHandler<Message> {

		text("время") {
			TestUtil.api.sendMessage(it.chat.id, "Время сервера: ${Date()}!")
		}

		// пример набора синонимом для команды
		text("пинг", "кинг") {
			TestUtil.api.sendMessage(it.chat.id,
				when(it.text!!) {
					"пинг" -> "ПОНГ!"
					"кинг" -> "КОНГ!"
					else -> "Как я здесь очутился???"
				}
			)
		}

		// выдача информации по пользователю
		text("мой ид", "/me") {
			val from = it.from!!
			TestUtil.api.sendMessage(it.chat.id, "ID ${from.firstName} ${from.lastName} (@${from.username}) равен: ${from.id}")
		}

		// команды с регулярным выражениями
		regex("""рандом (\d+) (\d+)""") { message, params ->
			var first = params[1].toInt()
			var second = params[2].toInt()
			if (second < first)
				first = second.also { second = first }

			TestUtil.api.sendMessage(message.chat.id, "🎲 Случайное значение в диапазоне [$first..$second] выпало на ${(first..second).random()}")
		}

		// возможность прикрепить готовую функцию с подходящей сигнатурой (message: Message) -> Unit
		text(listOf("как дела?", "привет, как дела?"), ::answerHowAreYou)

		// возможность прикрепить готовый класс типа Command
		text(listOf("кто ты?", "кто ты"), AnswerWhoAreYou()::run)

		regex("""кто я\??""", "(?:мой )?профиль") { message, params ->
			val from = message.from!!
			TestUtil.api.sendMessage(message.chat.id, "Вы ${from.firstName} ${from.lastName} [@${from.username}]. ID = ${from.id}")
		}
	}
}