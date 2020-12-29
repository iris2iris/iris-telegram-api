package iris.tg.webhook

import iris.tg.TgUpdateProcessor

/**
 * @created 02.11.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
class TgWebhookBot(
	private val server: TgWebhookRequestServer,
	private val buffer: TgWebhookReadWriteBuffer,
	private val updateProcessor: TgUpdateProcessor
) : Runnable {

	private var working = true

	override fun run() {
		server.start()
		working = true
		val thisThread = thread ?: Thread.currentThread()
		while (!thisThread.isInterrupted && working) {
			val items = buffer.retrieve()
			if (items.isEmpty()) continue
			updateProcessor.processUpdates(items)
		}
	}

	private var thread: Thread? = null

	fun startPolling() {
		thread = Thread(this).also { it.start() }
	}

	fun stop() {
		server.stop(5)
		working = false
		thread?.interrupt()
	}
}