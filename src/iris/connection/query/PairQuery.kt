package iris.connection.query

import iris.connection.StringBuilderUrlEncoder
import java.nio.charset.StandardCharsets

open class PairQuery(initialCapacity: Int = 5) : MutableQuery {

	private val list: ArrayList<Pair<String, Any?>> = ArrayList(initialCapacity)

	constructor(vararg pairs: Pair<String, Any?>) : this(pairs.size) {
		list += pairs
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
		if (list.isEmpty()) return
		for ((key, value) in list) {
			encode(sb, key as String)
			sb.append('=')
			value?.apply { encode(sb, this.toString()) }
			sb.append("&")
		}
	}

	override fun set(key: String, value: Any?) {
		list += key to value
	}

	override fun toList(): List<Pair<String, Any?>> {
		return list
	}

	override fun toMap(): Map<String, Any?> {
		return list.toMap()
	}
}