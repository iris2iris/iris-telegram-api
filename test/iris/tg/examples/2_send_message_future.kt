package iris.tg.examples

import iris.tg.api.response.SendMessageResponse
import iris.tg.tgApiFuture

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

	// Создаём объект TgApiFuture с подготовленными по умолчанию настройками.
	// Спецификой данного объекта являются неблокирующие запросы
	val api = tgApiFuture(token)

	println("Запускаем работу асинхронных запросов\n")

	val res = api.sendMessage(toId, "💝 Это сообщение отправлено с помощью Kotlin")
	println("Первый запрос без задержек")
	val res2 = api.sendMessage(toId, "💝 Второе сообщение отправлено с помощью Kotlin")
	println("Второй запрос без задержек")

	// Создадим намеренную ошибку
	val res3 = api.sendMessage(1, "💝 Третье сообщение отправлено с помощью Kotlin")
	println("Третий запрос без задержек\n")

	println("Ждём ответ 1:")
	println("message_id=" + res.get()?.result?.messageId)

	//////////////////////////////
	println("\nЖдём ответ 2 и проверим есть там результат или ошибка:")

	println(res2.get()?.let(::testResponse)) // Хороший запрос

	println("\nЖдём ответ 3 и проверим есть там результат или ошибка:")
	println(res3.get()?.let(::testResponse)) // Запрос с ошибкой
}

fun testResponse(response: SendMessageResponse): String {
	return if (response.ok)
		"Ответ получен: message_id=" + response.result!!.messageId
	else
		with(response.error!!) { "Возникла ошибка: $description ($errorCode)" }
}