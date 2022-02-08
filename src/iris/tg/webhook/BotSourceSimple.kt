package iris.tg.webhook

class BotSourceSimple(private val gb: BotSource.BotData) : BotSource {

	override fun getBot(request: TgWebhookRequestHandler.Request) = gb
}