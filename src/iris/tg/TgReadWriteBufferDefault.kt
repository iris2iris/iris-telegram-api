package iris.tg

import java.util.logging.Logger

/**
 * @created 02.11.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TgReadWriteBufferDefault<T>(private val maxQueueSize: Int) : TgReadWriteUpdateBuffer<T> {

	protected var queue: ArrayDeque<T> = ArrayDeque(maxQueueSize)
	private val queueWait = Object()

	companion object {
		private val logger = Logger.getLogger("iris.tg")
	}

	override fun write(event: T) {
		synchronized(queueWait) {
			if (queue.size < maxQueueSize) {
				queue += event
				queueWait.notify()
			} else {
				queueIsFull()
			}
		}
	}

	override fun write(events: List<T>) {
		synchronized(queueWait) {
			val eventsSize = events.size
			val size = queue.size
			when {
				queue.size + eventsSize <= maxQueueSize -> {
					queue += events
					queueWait.notify()
				}
				queue.size >= maxQueueSize -> {
					queueIsFull()
				}
				else -> { // когда частично влазит, частично переполнено
					queue += events.subList(0, maxQueueSize - size)
					queueIsFull()
					write(events.subList(maxQueueSize - size, events.size))
				}
			}
		}
	}

	override fun write(events: Array<T>) {
		write(events.asList())
	}

	open fun queueIsFull() {
		logger.warning { "Callback API queue is full (${queue.size} elements). Clearing..." }
		queue.clear()
	}

	override fun readAll(wait: Boolean): List<T> {
		return read(0, wait)
	}

	override fun read(limit: Int, wait: Boolean): List<T> {
		synchronized(queueWait) {
			val queue = this.queue
			do {
				if (queue.size != 0) {
					val res = if (limit == 0 || queue.size <= limit) {
						this.queue = ArrayDeque(maxQueueSize)
						queue
					} else {
						val a = ArrayList<T>(limit)
						for (i in 1..limit)
							a += queue.removeFirst()
						a
					}
					return res
				}
				if (!wait)
					return emptyList()
				queueWait.wait()
			} while (true)
		}
	}

	override fun read(dest: MutableList<T>, limit: Int, wait: Boolean): Int {
		synchronized(queueWait) {
			val queue = this.queue
			do {
				if (queue.size != 0) {
					val res = if (limit == 0 || queue.size <= limit) {
						this.queue = ArrayDeque(maxQueueSize)
						dest.addAll(queue)
						queue.size
					} else {
						for (i in 1..limit)
							dest += queue.removeFirst()
						limit
					}
					return res
				}
				if (!wait)
					return 0
				queueWait.wait()
			} while (true)
		}
	}
}