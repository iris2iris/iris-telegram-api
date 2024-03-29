package iris.tg.test

import iris.tg.api.TgApi

/**
 * @created 30.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {
	TestUtil.init()
	val properties = TestUtil.getProperties()
	val token = properties.getProperty("group.token")
	val toId = properties.getProperty("userTo.id").toLong()


	val api = TgApi(token)
	val res = api.sendMessage(toId, "💝 Это сообщение отправлено с помощью Kotlin")

	println("Ответ: " + res?.obj())
}