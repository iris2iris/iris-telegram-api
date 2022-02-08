package iris.tg.processors

import iris.tg.webhook.BotSource

/**
 * @created 05.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TgUpdateProcessor2Multibot<U>(private val botData: BotSource.BotData, private val multibotProcessor: TgUpdateMultibotProcessor<U>) : TgUpdateProcessor<U> {

	override fun processUpdates(updates: List<U>) {
		multibotProcessor.processUpdates(botData, updates)
	}

	override fun processUpdate(update: U) {
		multibotProcessor.processUpdate(botData, update)
	}
}