package iris.tg.test

import iris.tg.TgEventHandlerAdapter
import iris.tg.api.TgApi
import iris.tg.webhook.GroupbotSource
import iris.tg.webhook.TgWebhookBotBuilder
import iris.tg.event.Message
import iris.util.Options
import kotlin.system.exitProcess

/**
 * @created 29.12.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {

	TestUtil.init()
	val props = TestUtil.getProperties()
	val token = props.getProperty("group.token")
	val webhookUrl = props.getProperty("webhook.url")
	val groupId = props.getProperty("group.id").toInt()

	val api = TgApi(token)
	val r = api.request("setWebhook", Options("url" to webhookUrl))
	println(r?.obj())

	val handler = object : TgEventHandlerAdapter() {
		override fun processMessage(message: Message) {

			if (message.text == "стоп") {
				val r = api.request("setWebhook", Options("url" to ""))
				println(r?.obj())
				exitProcess(0)
			}

			println("New message: " + message.text)
		}
	}

	val gb = TgWebhookBotBuilder.build {
		groupbot = GroupbotSource.Groupbot(groupId)
		path = "/telegram"
		eventHandler = handler
		tgTimeVsLocalTimeDiff = -2*60*1000L
	}
	gb.run()
}