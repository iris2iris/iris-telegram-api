package iris.connection.query

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * @created 11.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Query {
	fun <A: StringBuilder>joinTo(sb: A): A
	fun toList(): List<Pair<String, Any?>>
	fun toMap(): Map<String, Any?>

	companion object {
		fun encode(o: String): String? {
			return URLEncoder.encode(o, StandardCharsets.UTF_8)
		}
	}
}

interface MutableQuery : Query {
	operator fun set(key: String, value: Any?)
}

fun query(key: String, value: Any?): Query = SinglePairQuery(key, value)

fun query(pair: Pair<String, Any?>): Query = SinglePairQuery(pair.first, pair.second)

fun query(vararg items: Pair<String, Any?>): Query {
	// Обычно элементов меньше 8, поэтому лучше создать массив под количество передаваемых элементов
	return RowArrayQuery(items.size, *items)
}

fun mutableQuery(): MutableQuery = PairArrayQuery()

// TODO: А здесь лучше по одному перенести в basic query или так пойдёт? Говорят такое `*items` клонирует массив
fun mutableQuery(vararg items: Pair<String, Any?>): MutableQuery = RowArrayQuery(*items)

fun mutableQuery(initialCapacity: Int, vararg items: Pair<String, Any?>): MutableQuery = RowArrayQuery(initialCapacity, *items)

fun mutableQuery(initialCapacity: Int): MutableQuery = RowArrayQuery(initialCapacity)
