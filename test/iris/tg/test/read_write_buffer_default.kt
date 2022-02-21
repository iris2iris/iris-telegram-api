package iris.tg.test

import iris.tg.TgReadWriteBufferDefault
import iris.tg.api.items.Message
import iris.tg.pojo.items.Chat_Pojo
import iris.tg.pojo.items.Message_Pojo
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.jvm.internal.Ref
import kotlin.system.exitProcess

/**
 * @created 09.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
fun main() {
	//writeSingles()
	//writeList(waitToWrite = 10L) // стандартное ожидание
	//writeList(waitToWrite = 0L) // вечное
	//writeList(waitToWrite = -1L) // не ждать совсем
	//writeList(bufferSize = 200, waitToWrite = TgReadWriteBufferDefault.WAIT_TIME_DONT_WAIT) // не ждать совсем, но с большим буфером
	//writeList(bufferSize = 1, waitToWrite = TgReadWriteBufferDefault.WAIT_TIME_DONT_WAIT) // очень короткий буфер
	//writeList(bufferSize = 100, waitToWrite = TgReadWriteBufferDefault.WAIT_TIME_DONT_WAIT) // очень короткий буфер
	writeList(bufferSize = 500, waitToWrite = -1, writeThreads = 20) // очень короткий буфер
}

fun writeSingles() {
	val atomic = AtomicInteger(0)
	val b = object : TgReadWriteBufferDefault<Message>(20) {
		override fun queueIsFull() {
			synchronized(atomic) {
				atomic.set(atomic.get() + queue.size)
				println("Got full: $atomic")
			}
			println("FULL: $queue")
			queue.clear()
		}

		override fun eventsRejected(events: List<Message>) {
			synchronized(atomic) {
				atomic.set(atomic.get() + events.size)
				println("Events rejected ${events.size}: $atomic")
			}
		}
	}


	val pool = (0..10).map {
		Thread {
			while (true) {
				val d = b.readAll()
				synchronized(atomic) {
					atomic.set(atomic.get() + d.size)
					println("Got: $atomic")
				}
				//println(Thread.currentThread().id.toString() + ": " + d)
			}
		}
	}
	for (i in pool)
		i.start()


	/*val writers = Executors.newFixedThreadPool(20)
	repeat(50_000) {
		writers.execute {
			b.write(Message_Pojo(it, Chat_Pojo(it.toLong(), "private")))
		}
	}*/
	val repeats = 20
	val total = 50_000
	val iterations = total/repeats
	repeat(repeats) {
		Thread() {
			for (i in it*iterations until (it + 1)*iterations)
				b.write(Message_Pojo(it, Chat_Pojo(it.toLong(), "private")))
		}.start()
	}

	for (i in pool)
		i.join()
}

fun writeList(bufferSize: Int = 20, waitToWrite: Long = 10L, writeThreads: Int = 20) {

	val atomic = Ref.IntRef()
	val rejected = Ref.IntRef()
	val full = Ref.IntRef()

	val b = object : TgReadWriteBufferDefault<Message>(bufferSize, waitToWrite) {
		override fun queueIsFull() {
			synchronized(atomic) {
				atomic.element += queue.size
				full.element += queue.size
				println("Got full: $atomic")
			}
			println("FULL: $queue")
			queue.clear()
		}

		override fun eventsRejected(events: List<Message>) {
			synchronized(atomic) {
				atomic.element += events.size
				println("Events rejected ${events.size}: $atomic")
				rejected.element += events.size
			}
		}
	}

	val superWait = Object()

	val pool = (0 until 10).map {
		Thread {
			while (true) {
				val d = b.readAll()
				println(Thread.currentThread().id.toString() + ": " + d)
				synchronized(atomic) {
					atomic.element += d.size
					println("Got: $atomic")
					if (atomic.element >= 50000)
						synchronized(superWait) { superWait.notify() }
				}
			}
		}
	}
	for (i in pool)
		i.start()

	val arr = (1L..50000L).map { Message_Pojo(it.toInt(), Chat_Pojo(it, "private")) }.chunked(25)
	val writers = Executors.newFixedThreadPool(writeThreads)
	for (a in arr)
		writers.execute { b.write(a)/*; Thread.sleep(20L)*/ }



	synchronized(superWait) {
		superWait.wait(5000L)
	}
	println("\nTotal: $atomic")
	println("Total rejected: $rejected")
	println("Total full: $full")
	exitProcess(0)
	/*for (i in pool)
		i.join()*/
}