package iris.tg.processors

import iris.tg.api.items.Update
import iris.tg.webhook.BotSource

/**
 * @created 05.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TgUpdateMultibotProcessorToUpdateProcessor<T>(private val simpleProcessor: TgUpdateProcessor<T>) : TgUpdateMultibotProcessor<T> {
	override fun processUpdates(fromBot: BotSource.BotData, updates: List<T>) {
		simpleProcessor.processUpdates(updates)
	}

	override fun processUpdate(fromBot: BotSource.BotData, update: T) {
		simpleProcessor.processUpdates(listOf(update))
	}
}