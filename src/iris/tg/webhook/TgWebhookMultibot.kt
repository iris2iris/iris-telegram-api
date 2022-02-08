package iris.tg.webhook

import iris.tg.TgReadWriteUpdateBuffer
import iris.tg.api.items.UpdateExt
import iris.tg.processors.TgUpdateProcessor

/**
 * @created 02.11.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
@Deprecated("Заменили на QueuedService")
class TgWebhookMultibot(
	private val server: TgWebhookRequestServer,
	private val updateProcessor: TgUpdateProcessor<UpdateExt>,
	private val buffer: TgReadWriteUpdateBuffer<UpdateExt>,
) : Runnable {

	/*constructor(server: TgWebhookRequestServer, handler: TgEventSingleHandler, buffer: TgReadWriteUpdateBuffer<UpdateExt>)
		: this(server, TgUpdateExtToSimpleProcessor(TgUpdateProcessorSingle(handler)), buffer)

	constructor(server: TgWebhookRequestServer, handler: TgEventPackHandler, buffer: TgReadWriteUpdateBuffer<UpdateExt>)
			: this(server, TgUpdateExtToSimpleProcessor(TgUpdateProcessorPack(handler)), buffer)*/

	private var working = true

	override fun run() {
		server.start()
		working = true
		val thisThread = thread ?: Thread.currentThread()
		while (!thisThread.isInterrupted && working) {
			val items = buffer.readAll()
			if (items.isEmpty()) continue
			updateProcessor.processUpdates(items)
		}
	}

	private var thread: Thread? = null

	fun startPolling() {
		thread?.interrupt()
		thread = Thread(this).also { it.start() }
	}

	fun stop() {
		server.stop(5)
		working = false
		thread?.interrupt()
	}
}