# Iris Telegram API

Ещё одна библиотека по работе с Telegram API на **Kotlin** 💖

Гибкая система получения данных от Telegram. После обновления Telegram API вам не придётся ждать 
обновления в ваших прежних используемых библиотеках. Все данные будут доступны сразу после обновлений самого Telegram API.

## Как скачать и установить?

##### Прямая ссылка:

- Вы можете скачать [подготовленные релизы](https://github.com/iris2iris/iris-telegram-api/releases), чтобы скачать JAR файл напрямую.
- Также вам необходимо скачать зависимость — JAR файл [Iris JSON Parser](https://github.com/iris2iris/iris-telegram-api/releases/download/v0.1/iris-json-parser.jar)

## Как это использовать

### Простой VkApi

```kotlin
val api = TgApi(token)
val res = api.sendMessage(toId, "💝 Это сообщение отправлено с помощью Kotlin")

println("Ответ: " + res?.obj())
```

### VkApi методом Future

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

Все приведённые выше примеры доступны в пакете [iris.tg.test](https://github.com/iris2iris/iris-telegram-api/blob/master/test/iris/tg/test)

## Дополнительная информация

**[Iris Telegram API](https://github.com/iris2iris/iris-telegram-api)** использует библиотеку **[Iris JSON Parser](https://github.com/iris2iris/iris-json-parser-kotlin)** для обработки ответов от сервера Telegram. Загляните ознакомиться =)

#### Не забывайте поставить звёзды, если этот инструмент оказался вам полезен ⭐