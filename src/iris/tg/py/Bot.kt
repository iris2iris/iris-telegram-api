package iris.tg.py

import iris.tg.TgService
import iris.tg.api.TgApiObjFuture
import iris.tg.api.TgApiObject
import iris.tg.longpoll.longPollQueuedBot
import iris.tg.py.items.PyMessage
import iris.tg.trigger.TgEventTriggerHandler

/**
 * @created 08.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class Bot(token: String): TgService {

	private val trigger = TgEventTriggerHandler<PyMessage>()

	private val listener = longPollQueuedBot {
		val handler = PyResponseHandler()
		val handlerApi = TgApiObjFuture(token, handler)
		handler.api = handlerApi

		api = TgApiObject(token, handler)

		updateProcessor = processor(trigger)
	}

	override fun start() {
		listener.start()
	}

	override fun stop() {
		listener.stop()
	}

	override fun join() {
		listener.join()
	}

	override fun run() {
		listener.run()
	}

	fun run_forever() = run()

	val on = Triggers()

	inner class Triggers {

		fun message(action: (PyMessage) -> Unit) {
			trigger.onMessage(action)
		}

		fun edit(action: (PyMessage) -> Unit) {
			trigger.onMessageEdit(action)
		}
	}
}