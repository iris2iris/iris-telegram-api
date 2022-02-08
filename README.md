# Iris Telegram API

Ещё одна библиотека по работе с Telegram API на **Kotlin** 💖

Гибкая система получения данных от Telegram. После обновления Telegram API вам не придётся ждать 
обновления в ваших прежних используемых библиотеках. Все данные будут доступны сразу после обновлений самого Telegram API.

## Как скачать и установить?

##### Прямая ссылка:

- Вы можете скачать [подготовленные релизы](https://github.com/iris2iris/iris-telegram-api/releases), чтобы скачать JAR файл напрямую.
- Также вам необходимо скачать зависимость — JAR файл [Iris JSON Parser](https://github.com/iris2iris/iris-telegram-api/releases/download/v0.1/iris-json-parser.jar)

## Как это использовать

### Простой TgApi

```kotlin
val api = tgApi(token)
val res = api.sendMessage(toId, "💝 Это сообщение отправлено с помощью Kotlin")

println("Ответ: $res")
```

### TgApi методом Future

```kotlin
val api = tgApiFuture(token)

println("Запускаем работу асинхронных запросов\n")

val res = api.sendMessage(toId, "💝 Это сообщение отправлено с помощью Kotlin")
println("Первый запрос без задержек")

val res2 = api.sendMessage(toId, "💝 Второе сообщение отправлено с помощью Kotlin")
println("Второй запрос без задержек\n")

println("Теперь ждём ответ 1: " + res.get())
println("Теперь ждём ответ 2: " + res2.get())
```

### TgBotLongPoll — слушатель событий методом Long Poll
```kotlin
val api = tgApiFuture(token)

// Определяем обработчик событий
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
//listener.run() // Можно заблокировать дальнейшую работу потока, пока не будет остановлено
```

### TgCommandHandler

Возможность добавлять обработчики каждой текстовой команды отдельным обработчиком

```kotlin
val commandsHandler = TgCommandPackHandler<Message>()
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
```
##### Настройка карты команд с помощью DSL

```kotlin
commandsHandler += commands {
    "пинг" runs {
        api.sendMessage(it.peerId, "ПОНГ!")
    }

    "мой ид" runs {
        api.sendMessage(it.peerId, "Ваш ID равен: ${it.fromId}")
    }

    regex("""рандом (\d+) (\d+)""") runs { vkMessage, params ->

        var first = params[1].toInt()
        var second = params[2].toInt()
        if (second < first)
            first = second.also { second = first }

        api.sendMessage(vkMessage.peerId, "🎲 Случайное значение в диапазоне [$first..$second] выпало на ${(first..second).random()}")
    }
}
```

### Обработки событий методом onXxx

```kotlin
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
```

Все приведённые выше примеры доступны в пакете [iris.tg.test](https://github.com/iris2iris/iris-telegram-api/blob/master/test/iris/tg/test)

## Дополнительная информация

**[Iris Telegram API](https://github.com/iris2iris/iris-telegram-api)** использует библиотеку **[Iris JSON Parser](https://github.com/iris2iris/iris-json-parser-kotlin)** для обработки ответов от сервера Telegram. Загляните ознакомиться =)

### Благодарности
Спасибо @markelovstyle за код-ревью, замечания и предложения

⭐ **Не забывайте поставить звёзды, если этот инструмент оказался вам полезен**