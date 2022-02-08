package iris.tg.examples

import iris.tg.keyboard.callback.CallbackMonster
import iris.tg.longpoll.TgLongPoll
import iris.tg.tgApi
import iris.tg.tgApiFuture

/**
 * @created 07.02.2022
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {
	// Инициализируем тестовые данные. В рабочих проектах этого делать не нужно
	TestUtil.init()
	val props = TestUtil.properties
	val token = props.getProperty("bot.token")
	val toId = props.getProperty("userTo.id").toLong()

	// Создаём объект TgApi с неблокирующими запросами для отправки сообщений
	val api = tgApiFuture(token)

	val callbackHandler = CallbackMonster(api)

	val handlerKeyboard = callbackHandler.keyboard {
		row {
			button("Я реагирую!") {
				api.sendMessage(it.message!!.chat.id, "Меня заставили отреагираовать: @${it.from.username}")
			}

			button("Я реагирую 2") {
				api.sendMessage(it.message!!.chat.id, "Меня заставили отреагираовать на второе: @${it.from.username}")
			}
		}
	}

	val singleButton = callbackHandler.singleButtonKeyboard("Я реагирую!") {
		api.sendMessage(it.message!!.chat.id, "Меня заставили отреагираовать: @${it.from.username}")
	}

	api.sendMessage(toId, "Кнопка с реакцией", reply_markup = api.json(handlerKeyboard))

	val listener = TgLongPoll(tgApi(token), callbackHandler)
	listener.startPolling()

	listener.join()
}