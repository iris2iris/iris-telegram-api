package iris.tg.connection.query

import iris.tg.connection.StringBuilderUrlEncoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

open class BasicQuery(initialCapacity: Int = 5) : MutableQuery {

	private val keys: ArrayList<String> = ArrayList(initialCapacity)
	private val values: ArrayList<Any?> = ArrayList(initialCapacity)

	constructor(vararg pairs: Pair<String, Any?>) : this(pairs.size) {
		for ((key, value) in pairs) {
			keys += key
			values += value
		}
	}

	override fun <A : StringBuilder> joinTo(sb: A): A {
		encodeOptions(sb)
		return sb
	}

	override fun toString(): String {
		return joinTo(StringBuilder()).toString()
	}

	private fun encode(sb: StringBuilder, o: String) {
		StringBuilderUrlEncoder.encode(sb, o, StandardCharsets.UTF_8)
	}

	private fun encodeOptions(sb: StringBuilder) {
		if (keys.isEmpty()) return
		for (i in keys.indices) {
			val key = keys[i]
			val value = values[i]
			encode(sb, key)
			sb.append('=')
			value?.apply { encode(sb, this.toString()) }
			sb.append("&")
		}
	}

	override fun set(key: String, value: Any?) {
		keys += key
		values += value
	}

	override fun toList(): List<Pair<String, Any?>> {
		val res = ArrayList<Pair<String, Any?>>(keys.size)
		for (i in keys.indices) {
			val key = keys[i]
			val value = values[i]
			res += key to value
		}
		return res
	}

	override fun toMap(): Map<String, Any?> {
		val res = HashMap<String, Any?>(keys.size)
		for (i in keys.indices) {
			val key = keys[i]
			val value = values[i]
			res[key] = value
		}
		return res
	}
}