package iris.tg.test

import iris.tg.api.TgApiFuture

/**
 * @created 30.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {
	TestUtil.init()
	val properties = TestUtil.getProperties()
	val token = properties.getProperty("group.token")
	val toId = properties.getProperty("userTo.id").toLong()


	val api = TgApiFuture(token)

	println("Запускаем работу асинхронных запросов\n")

	val res = api.sendMessage(toId, "💝 Это сообщение отправлено с помощью Kotlin")
	println("Первый запрос без задержек")

	val res2 = api.sendMessage(toId, "💝 Второе сообщение отправлено с помощью Kotlin")
	println("Второй запрос без задержек\n")

	println("Теперь ждём ответ 1: " + res.get()?.obj())
	println("Теперь ждём ответ 2: " + res2.get()?.obj())
}