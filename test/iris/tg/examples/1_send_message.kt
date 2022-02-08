package iris.tg.examples

import iris.tg.tgApi

/**
 * @created 30.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {

	// Инициализируем тестовые данные. В рабочих проектах этого делать не нужно
	TestUtil.init()
	val properties = TestUtil.properties
	val token = properties.getProperty("bot.token")
	val toId = properties.getProperty("userTo.id").toLong()


	// Создаём объект TgApi с подготовленными по умолчанию настройками
	val api = tgApi(token)

	// Наш первый запрос к серверу Телеграм
	val res = api.sendMessage(toId, "💝 Это сообщение отправлено с помощью Kotlin")
	println("Ответ: ${res.result}")

	// Доступ к конкретному элементу объекта
	if (res.ok)
		println("ID сообщения: ${res.result!!.messageId}")
	else
		println("Получена ошибка: ${res.error}")
}