package iris.tg.processors

import iris.tg.webhook.BotSource

/**
 * @created 31.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgUpdateMultibotProcessor<T> {
	fun processUpdates(fromBot: BotSource.BotData, updates: List<T>)
	fun processUpdate(fromBot: BotSource.BotData, update: T)
}