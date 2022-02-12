package iris.tg.test

import iris.tg.connection.query.BasicQuery
import iris.tg.connection.query.PairArrayQuery
import iris.tg.connection.query.RowArrayQuery
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * @created 11.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
fun main() {
	basicQueryTest(30, 10000)
	basicRawQueryTest(30, 10000)
	mapTest(30, 10000)

	val size = 7
	val repeats = 1_000_000

	repeat(3) {
		System.gc()
		mapTest(size, repeats)

		System.gc()
		basicQueryTest(size, repeats)

		System.gc()
		basicRawQueryTest(size, repeats)
	}


}

fun basicQueryTest(size: Int, repeats: Int) {
	val data = generateData(size)
	val start = System.currentTimeMillis()
	repeat(repeats) {
		val options = BasicQuery()
		//repeat(size) {
		for (i in data)
		//options[diapa.random().toString()] = diapa.random().toString()
			options[i] = i
		//}
		val t = options.toString()
	}
	val end = System.currentTimeMillis()
	println("Basic Query: ${end - start}")
}

fun basicRawQueryTest(size: Int, repeats: Int) {
	val data = generateData(size)
	val start = System.currentTimeMillis()
	repeat(repeats) {
		val options = RowArrayQuery()
		//repeat(size) {
		for (i in data)
			//options[diapa.random().toString()] = diapa.random().toString()
			options[i] = i
		//}
		val t = options.toString()
	}
	val end = System.currentTimeMillis()
	println("RAW Query: ${end - start}")
}

fun generateData(size: Int): List<String> {
	val diapa = (10_000..99_000)
	val letters = "йцукенгшщзхфывапролджэячсмитьбю"
	val data = (1..size).map {
		val sb = StringBuilder()
		diapa.random().toString().toCharArray().forEach {
			sb.append(it).append(' ').append(letters.random())
		}
		sb.toString()
	}
	return data
}

fun mapTest(size: Int, repeats: Int) {
	val data = generateData(size)
	val start = System.currentTimeMillis()
	repeat(repeats) {
		val options = HashMap<String, Any?>()
		//repeat(size) {
		for (i in data)
			//options[diapa.random().toString()] = diapa.random().toString()
			options[i] = i
		//}
		val t = encodeOptions(options)
	}
	val end = System.currentTimeMillis()
	println("Map: ${end - start}")
}

internal fun encode(o: String): String? {
	// TODO: нужна функция со StringBuilder
	return URLEncoder.encode(o, StandardCharsets.UTF_8)
}

internal fun encodeOptions(map: Map<String, Any?>): String {
	if (map.isEmpty()) return ""
	val sb = StringBuilder()
	for ((key, value ) in map) {
		sb.append(encode(key)).append('=')
			.append(encode(value.toString())).append("&")
	}
	return sb.toString()
}