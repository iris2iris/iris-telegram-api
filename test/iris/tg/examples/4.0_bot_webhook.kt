package iris.tg.examples

import iris.tg.api.items.Message
import iris.tg.processors.single.TgEventMessageSingleHandlerAdapter
import iris.tg.processors.single.TgEventMessageSingleHandlerAdapterBasicTypes
import iris.tg.tgApi
import iris.tg.webhook.AddressTesterDefault
import iris.tg.webhook.webhookBot
import kotlin.system.exitProcess

/**
 * @created 29.12.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {

	// Инициализируем тестовые данные. В рабочих проектах этого делать не нужно
	TestUtil.init()
	val props = TestUtil.properties
	val token = props.getProperty("bot.token")
	val webhookUrl = props.getProperty("webhook.url")
	val addressTesterIP = props.getProperty("webhook.addressTesterIP")

	val api = tgApi(token)
	api.setWebhook(webhookUrl)

	// Уже знакомый нам обработчик событий
	val handler = object : TgEventMessageSingleHandlerAdapterBasicTypes() {

		override fun text(message: Message) {
			if (message.text == "стоп") {
				val r = api.deleteWebhook()
				println(r?.result)
				exitProcess(0)
			}

			println("New message: " + message.text)
		}

	}

	// Настройка бота
	val bot = webhookBot {
		addressTester = if (addressTesterIP != null) AddressTesterDefault(addressTesterIP) else null
		path = "/telegram"
		updateProcessor = toProcessor(handler)
		port = 8000
	}
	bot.run()
}

