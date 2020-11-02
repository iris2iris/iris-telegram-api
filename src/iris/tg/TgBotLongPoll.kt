package iris.tg

import iris.json.JsonArray
import iris.tg.api.TgApi
import java.util.logging.Logger

/**
 * @created 30.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
open class TgBotLongPoll(private val api: TgApi, private val updateProcessor: TgUpdateProcessor) : Runnable {

	constructor(token: String, handler: TgEventHandler) : this(TgApi(token), TgUpdateProcessorDefault(handler))

	companion object {
		private val logger = Logger.getLogger("iris.tg")
	}

	var isWorking = true

	override fun run() {
		isWorking = true
		var offset = -1L
		var updates = api.getUpdates(offset, 10)
		if (updates == null) {
			logger.warning("NO RESPONSE")
			return
		}

		if (updates["ok"].isNull()) {
			val errorCode = updates["error_code"].asIntOrNull()
			if (errorCode == 502) {}
			else {
				logger.warning { "NOT OK" + updates?.obj() }
			}
			return
		}
		val items = (updates["result"] as JsonArray)

		offset = if (items.isNotEmpty()) items.getList().last()["update_id"].asLong() + 1 else -1L
		val thisThread = Thread.currentThread()
		while (!thisThread.isInterrupted && isWorking)  {
			updates = api.getUpdates(offset, 10)
			if (updates == null) {
				Thread.sleep(3 * 1000L)
				continue
			}

			if (!updates["ok"].asBoolean()) {
				val errorCode = updates["error_code"].asInt()
				if (errorCode == 502) {
					Thread.sleep(10 * 1000L)
					continue
				} else {
					logger.warning { "NOT OK" + updates.obj() }
					return
				}

			}

			val items = (updates["result"] as JsonArray).getList()
			if (items.isEmpty())
				continue
			offset = items.last()["update_id"].asLong() + 1
			updateProcessor.processUpdates(items)
		}
	}

	private lateinit var thread: Thread

	open fun startPolling() {
		thread = Thread(this)
		thread.start()
	}

	open fun join() {
		if (!this::thread.isInitialized)
			return
		thread.join()
	}

	fun stop() {
		isWorking = false
	}
}