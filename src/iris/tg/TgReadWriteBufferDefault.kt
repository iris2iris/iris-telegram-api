package iris.tg

import java.util.logging.Logger
import kotlin.collections.ArrayDeque
import kotlin.collections.ArrayList

/**
 * @created 02.11.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
@Suppress("MemberVisibilityCanBePrivate")
open class TgReadWriteBufferDefault<T>(private val maxQueueSize: Int, private val maxTimeToWrite: Long = WAIT_TIME_DONT_WAIT) : TgReadWriteUpdateBuffer<T> {

	protected var queue: ArrayDeque<T> = ArrayDeque(maxQueueSize)

	private val queueModifyLock = Object()
	private val notEmpty = Object()
	private val notFull = Object()

	companion object {

		const val WAIT_TIME_FOREVER = 0L
		const val WAIT_TIME_DONT_WAIT = -1L

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
			//synchronized(noSuccessLock) {
				if (maxTimeToWrite > 0) { // процесс ожидания
					val startTime = System.currentTimeMillis()
					var timeToWait = maxTimeToWrite
					while (timeToWait > 0L) {
						synchronized(notFull) { notFull.wait(timeToWait) }
						synchronized(queueModifyLock) {
							if (queue.size < maxQueueSize) {
								queue += event
								synchronized(notEmpty) { notEmpty.notify() }
								return
							}
						}
						timeToWait = maxTimeToWrite + startTime - System.currentTimeMillis()
					}
				} else if (maxTimeToWrite == 0L) {
					// TODO: наверно, здесь лучше с isInterrupted?
					while (true) {
						synchronized(notFull) { notFull.wait(0L) }
						synchronized(queueModifyLock) {
							if (queue.size < maxQueueSize) {
								queue += event
								synchronized(notEmpty) { notEmpty.notify() }
								return
							}
						}
					}
				}

				synchronized(queueModifyLock) {
					if (queue.size >= maxQueueSize) {
						queueIsFull()
						if (queue.size < maxQueueSize) {
							queue += event
							synchronized(notEmpty) { notEmpty.notify() }
							if (queue.size < maxQueueSize)
								synchronized(notFull) { notFull.notify() }
						}
					}
				}
			//}
		}
	}

	override fun write(events: List<T>) {
		write(events, 0)
	}

	private fun write(events: List<T>, depth: Int) {
		val eventsSize = events.size
		val wroteSize = synchronized(queueModifyLock) {
			addQueue(events)
		}

		if (wroteSize < eventsSize) {
			if (wroteSize == 0) {
				val subWroteSize = run t@ {
					if (maxTimeToWrite > 0) {
						val startTime = System.currentTimeMillis()
						var timeToWait = maxTimeToWrite
						while (timeToWait > 0L) {
							synchronized(notFull) { notFull.wait(timeToWait) }
							synchronized(queueModifyLock) {
								val res = addQueue(events)
								if (res != 0)
									return@t res
							}
							timeToWait = maxTimeToWrite + startTime - System.currentTimeMillis()
						}
					} else if (maxTimeToWrite == 0L) {
						// TODO: наверно, здесь лучше с isInterrupted?
						while (true) {
							synchronized(notFull) { notFull.wait(0L) }
							synchronized(queueModifyLock) {
								val res = addQueue(events)
								if (res != 0)
									return@t res
							}
						}
					}

					synchronized(queueModifyLock) {
						if (queue.size >= maxQueueSize) {
							queueIsFull()
							// перепроверяем размер очереди, потому что queueIsFull может быть переопределена и не сбрасывать очередь
							if (queue.size < maxQueueSize) {
								val res = addQueue(events)
								if (res != 0) {
									if (queue.size < maxQueueSize)
										synchronized(notFull) { notFull.notify() }
									return@t res
								}
								synchronized(notFull) { notFull.notify() }
							}
						}
					}
					0
				}
				if (subWroteSize < eventsSize) {
					val eventsLeft = if (subWroteSize == 0) events else events.subList(subWroteSize, eventsSize)
					if (depth < 10)
						write(eventsLeft, depth + 1)
					else
						eventsRejected(eventsLeft)
				}
			} else {
				val eventsLeft = events.subList(wroteSize, eventsSize)
				if (depth < 10)
					write(eventsLeft, depth + 1)
				else
					eventsRejected(eventsLeft)
			}
		}
	}

	protected open fun eventsRejected(events: List<T>) {
		logger.warning { "Events rejected: ${events.size}" }
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
				val writeSize = maxQueueSize - size
				queue += events.subList(0, writeSize)
				writeSize
			}
		}
		synchronized(notEmpty) { notEmpty.notify() }
		return wroteSize
	}

	override fun write(events: Array<T>) {
		write(events.asList())
	}

	open fun queueIsFull() {
		logger.warning { "Queue is full (${queue.size} elements). Clearing..." }
		queue.clear()
	}

	override fun readAll(wait: Boolean): List<T> {
		return read(0, wait)
	}

	override fun read(limit: Int, wait: Boolean): List<T> {

		do {
			val res = synchronized(queueModifyLock) {
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

					res
				} else
					null
			}
			if (res != null) {
				synchronized(notFull) { notFull.notify() }
				return res
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