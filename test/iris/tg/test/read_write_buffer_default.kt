package iris.tg.test

import iris.tg.TgReadWriteBufferDefault
import iris.tg.api.items.Message
import iris.tg.pojo.items.Chat_Pojo
import iris.tg.pojo.items.Message_Pojo
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/**
 * @created 09.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
fun main() {
	//writeSingles()
	writeList()
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

fun writeList() {

	val atomic = AtomicInteger(0)

	val b = object : TgReadWriteBufferDefault<Message>(2) {
		override fun queueIsFull() {
			synchronized(atomic) {
				atomic.set(atomic.get() + queue.size)
				println("Got full: $atomic")
			}
			println("FULL: $queue")
			queue.clear()
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
				println(Thread.currentThread().id.toString() + ": " + d)
			}
		}
	}
	for (i in pool)
		i.start()

	val arr = (1L..50000L).map { Message_Pojo(it.toInt(), Chat_Pojo(it, "private")) }.chunked(10)
	val writers = Executors.newFixedThreadPool(20)
	for (a in arr)
		writers.execute { b.write(a) }

	for (i in pool)
		i.join()
}