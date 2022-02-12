package iris.tg.examples

import iris.tg.api.items.Message
import iris.tg.longpoll.longPollQueued
import iris.tg.processors.single.TgEventMessageSingleHandlerAdapterBasicTypes
import iris.tg.tgApi

/**
 * @created 06.02.2022
 * @author [Ivan Ivanov](https://vk.com/irisism)
 * Пример запуска слушателя событий Телеграм методом long polling.
 *
 * В отличие от предыдущего примера, содержащегося в файле 3.0_bot_polling.kt,
 * бот не дожидается обработки всех полученных событий, а обращается за новыми.
 *
 * Полученные события собираются в очередь и ждут, когда их запросит обработчик событий.
 */
fun main() {

	// Инициализируем тестовые данные. В рабочих проектах этого делать не нужно
	TestUtil.init()
	val props = TestUtil.properties
	val token = props.getProperty("bot.token") ?: throw IllegalArgumentException()

	// TestUtil.api.deleteWebhook() // Раскомментировать в случае ошибки конфликта работающего webhook'а

	// Определяем обработчик получаемых событий
	val textHandler = object: TgEventMessageSingleHandlerAdapterBasicTypes() {
		override fun text(message: Message) {
			println("Сообщение от @" + message.from!!.username + ": " + message.text)
		}
	}

	//
	val bot = longPollQueued {
		api = tgApi(token)
		updateProcessor = processor(textHandler)
	}
	bot.run()
}