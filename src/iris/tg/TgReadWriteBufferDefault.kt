package iris.tg

import java.util.logging.Logger
import kotlin.collections.ArrayDeque
import kotlin.collections.ArrayList

/**
 * @created 02.11.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TgReadWriteBufferDefault<T>(private val maxQueueSize: Int, private val maxTimeToWrite: Long = 10L) : TgReadWriteUpdateBuffer<T> {

	protected var queue: ArrayDeque<T> = ArrayDeque(maxQueueSize)

	private val queueModifyLock = Object()
	private val notEmpty = Object()
	private val notFull = Object()
	private val noSuccessLock = Object()

	companion object {
		private val logger = Logger.getLogger("iris.tg")
	}

	override fun write(event: T) {
		val success = synchronized(queueModifyLock) {
			val isNotFull = queue.size < maxQueueSize
			if (isNotFull) {
				queue += event
				synchronized(notEmpty) { notEmpty.notify() }
			}
			isNotFull
		}

		if (!success) {
			synchronized(noSuccessLock) {
				val startTime = System.currentTimeMillis()
				var timeToWait = maxTimeToWrite
				do {
					synchronized(notFull) { notFull.wait(timeToWait) }
					synchronized(queueModifyLock) {
						if (queue.size < maxQueueSize) {
							queue += event
							synchronized(notEmpty) { notEmpty.notify() }
							return
						}
					}
					timeToWait = maxTimeToWrite + startTime - System.currentTimeMillis()
				} while (timeToWait > 0L)

				synchronized(queueModifyLock) {
					if (queue.size >= maxQueueSize) {
						queueIsFull()
						queue += event
						synchronized(notEmpty) { notEmpty.notify() }
						synchronized(notFull) { notFull.notify() }
					}
				}
			}
		}
	}

	override fun write(events: List<T>) {
		val eventsSize = events.size
		val wroteSize = synchronized(queueModifyLock) {
			addQueue(events)
		}

		if (wroteSize < eventsSize) {
			if (wroteSize == 0) {
				val subWroteSize = synchronized(noSuccessLock)t@ {
					val maxWaitTime = 10L
					val startTime = System.currentTimeMillis()
					var timeToWait = maxWaitTime
					do {
						synchronized(notFull) { notFull.wait(timeToWait) }
						synchronized(queueModifyLock) {
							if (queue.size < maxQueueSize) {
								return@t addQueue(events)
							}
						}
						timeToWait = maxWaitTime + startTime - System.currentTimeMillis()
					} while (timeToWait > 0L)

					synchronized(queueModifyLock) {
						if (queue.size >= maxQueueSize) {
							queueIsFull()
							synchronized(notFull) { notFull.notify() }
						}
					}
					0
				}
				if (subWroteSize < eventsSize)
					write(if (subWroteSize == 0) events else events.subList(subWroteSize, eventsSize))
			} else
				write(events.subList(wroteSize, eventsSize))
		}
	}

	private fun addQueue(events: List<T>): Int {
		val size = queue.size
		val eventsSize = events.size
		val wroteSize = when {
			maxQueueSize == size -> 0 // очередь полная

			queue.size + eventsSize <= maxQueueSize -> {
				queue += events
				eventsSize
			}

			else -> { // когда частично влазит, частично переполнено
				val wroteSize = maxQueueSize - size
				queue += events.subList(0, wroteSize)
				wroteSize
			}
		}
		synchronized(notEmpty) { notEmpty.notify() }
		return wroteSize
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

		do {
			synchronized(queueModifyLock) {
				val queue = this.queue
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
					synchronized(notFull) { notFull.notify() }
					return res
				}
			}

			if (!wait)
				return emptyList()
			synchronized(notEmpty) {notEmpty.wait() }
		} while (true)
	}

	override fun read(dest: MutableList<T>, limit: Int, wait: Boolean): Int {
		// TODO: Основательно ещё не тестировалось
		do {
			synchronized(queueModifyLock) {
				val queue = this.queue
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
					synchronized(notFull) { notFull.notify() }
					return res
				}
			}

			if (!wait)
				return 0
			synchronized(notEmpty) {notEmpty.wait() }
		} while (true)
	}
}