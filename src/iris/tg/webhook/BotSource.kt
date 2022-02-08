package iris.tg.webhook

interface BotSource {
	/**
	 * Извлекает информацию о группе из запроса, содержащуюся в URI или query.
	 *
	 * Например, URI может содержать такую информацию `/callback/fa33a6`, где код `fa33a6` сопоставляется с
	 * одной из имеющихся групп.
	 */
	fun getBot(request: TgWebhookRequestHandler.Request): BotData?

	open class BotData(val id: Long) {
		companion object {
			val zero = BotData(0)
		}
	}
}