/**
 * @created 04.02.2022
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {
	println("Warmup")
	System.gc()
	testCopy(20_000_000)
	System.gc()
	testRemove(20_000_000)
	System.gc()
	testIter(20_000_000)
	println("Goo")
	System.gc()
	testCopy(20_000_000)
	System.gc()
	testRemove(20_000_000)
	System.gc()
	testIter(20_000_000)
}

fun testCopy(amount: Int) {
	val queue = ArrayDeque<Int>()
	(1..amount).toCollection(queue)

	val t = System.currentTimeMillis()
	val l = queue.toList()
	queue.clear()
	println(System.currentTimeMillis() - t)
}

fun testIter(amount: Int) {
	val limit = amount
	val queue = ArrayDeque<Int>()
	(1..amount).toCollection(queue)
	val l = ArrayList<Int>(limit)

	val t = System.currentTimeMillis()

	val iter = queue.iterator()
	for (i in 1..limit) {
		l += iter.next()
		iter.remove()
	}
	println(System.currentTimeMillis() - t)
}

fun testRemove(amount: Int) {
	val limit = amount
	val queue = ArrayDeque<Int>()
	(1..amount).toCollection(queue)
	val l = ArrayList<Int>(limit)

	val t = System.currentTimeMillis()

	for (i in 1..limit) {
		l += queue.removeFirst()
	}

	println(System.currentTimeMillis() - t)
}