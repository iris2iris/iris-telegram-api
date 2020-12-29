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
val api = TgApi(token)
val res = api.sendMessage(toId, "💝 Это сообщение отправлено с помощью Kotlin")

println("Ответ: " + res?.obj())
```

### TgApi методом Future

```kotlin
val api = TgApiFuture(token)

println("Запускаем работу асинхронных запросов\n")

val res = api.sendMessage(toId, "💝 Это сообщение отправлено с помощью Kotlin")
println("Первый запрос без задержек")

val res2 = api.sendMessage(toId, "💝 Второе сообщение отправлено с помощью Kotlin")
println("Второй запрос без задержек\n")

println("Теперь ждём ответ 1: " + res.get()?.obj())
println("Теперь ждём ответ 2: " + res2.get()?.obj())
```

### TgBotLongPoll — слушатель событий методом Long Poll
```kotlin
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
listener.startPolling() // Можно запустить неблокирующего слушателя
listener.join() // Даст дождаться завершения работы слушателя
//listener.run() // Можно заблокировать дальнейшую работу потока, пока не будет остановлено
```

### TgCommandHandler

Возможность добавлять обработчики каждой текстовой команды отдельным обработчиком

```kotlin
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
listener.run()
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
listener.run()
```

##### Настройка карты событий с помощью DSL
```kotlin
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
    ))
}
```

Все приведённые выше примеры доступны в пакете [iris.tg.test](https://github.com/iris2iris/iris-telegram-api/blob/master/test/iris/tg/test)

## Дополнительная информация

**[Iris Telegram API](https://github.com/iris2iris/iris-telegram-api)** использует библиотеку **[Iris JSON Parser](https://github.com/iris2iris/iris-json-parser-kotlin)** для обработки ответов от сервера Telegram. Загляните ознакомиться =)

### Благодарности
Спасибо @markelovstyle за код-ревью, замечания и предложения

⭐ **Не забывайте поставить звёзды, если этот инструмент оказался вам полезен**