package iris.tg.examples

import iris.tg.api.items.UpdateExt
import iris.tg.processors.TgUpdateProcessor
import iris.tg.tgApi
import iris.tg.webhook.AddressTesterDefault
import iris.tg.webhook.BotSource
import iris.tg.webhook.TgWebhookRequestHandler
import iris.tg.webhook.webhookMultibot

/**
 * @created 05.02.2022
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {

	// Инициализируем тестовые данные. В рабочих проектах этого делать не нужно
	TestUtil.init()
	val props = TestUtil.properties
	val bot1Token = props.getProperty("bot.token")
	val bot1Id = props.getProperty("bot.id").toLong()


	val addressTesterIP = props.getProperty("webhook.addressTesterIP")
	val webhookBeginUrl = props.getProperty("multibot.webhook.url") ?: throw IllegalArgumentException("Для запуска данного примера необходимо назначить webhookBeginUrl")
	val bot2Token = props.getProperty("multibot.bot2.token") ?: throw IllegalArgumentException("Для запуска данного примера необходимо назначить token второго бота")
	val bot2Id = props.getProperty("multibot.bot2.id")?.toLong() ?: throw IllegalArgumentException("Для запуска данного примера необходимо назначить id второго бота")
	val uri = "/multibot/"

	tgApi(bot1Token).apply {
		setWebhook("$webhookBeginUrl$bot1Id")
			.apply(::println)
		getWebhookInfo().apply(::println)
	}

	tgApi(bot2Token).apply {
		setWebhook("$webhookBeginUrl$bot2Id")
			.apply(::println)
		getWebhookInfo().apply(::println)
	}

	////////////////////////////

	val myBotSource = object : BotSource {

		private val matcher = Regex("""$uri(\w+)""")

		override fun getBot(request: TgWebhookRequestHandler.Request): BotSource.BotData? {
			val m = matcher.matchEntire(request.requestUri.path) ?: return null
			val id = m.groupValues[1].toLong()
			if (!(id == bot1Id || id == bot2Id)) {
				println("Несанкционированный доступ с ID бота: $id")
				return null
			}
			return BotSource.BotData(id)
		}
	}

	///////////////////////////

	val processor = object : TgUpdateProcessor<UpdateExt> {
		override fun processUpdates(updates: List<UpdateExt>) {
			updates.forEach(this::processUpdate)
		}

		override fun processUpdate(update: UpdateExt) {
			println("Update received by bot with ID: " + update.forBot.id + " text: " + update.update)
		}
	}

	////////////////////////////

	val bot = webhookMultibot {
		botSource = myBotSource
		if (addressTesterIP != null) addressTester =  AddressTesterDefault(addressTesterIP)
		path = uri
		updateProcessor = processor
		port = 8000
		//tgVsLocalTimeDiff = TestUtil.tgVsLocalTimeDiff()
	}
	bot.run()
}