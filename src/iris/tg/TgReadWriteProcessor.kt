package iris.tg

import iris.tg.processors.TgUpdateProcessor

/**
 * @created 02.11.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TgReadWriteProcessor<T>(
	private val buffer: TgReadWriteUpdateBuffer<T>,
	private val updateProcessor: TgUpdateProcessor<T>
) : Runnable {

	private var working = true

	override fun run() {
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
		stop()
		thread = Thread(this).also { it.start() }
	}

	fun stop() {
		working = false
		if (thread != null) {
			thread!!.interrupt()
			thread = null
		}

	}
}