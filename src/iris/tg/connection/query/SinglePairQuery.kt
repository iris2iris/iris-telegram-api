package iris.tg.connection.query

import iris.tg.connection.StringBuilderUrlEncoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class SinglePairQuery(val key: String, val value: Any?): Query {

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
		encode(sb, key)
		sb.append('=')
		value?.apply { encode(sb, this.toString()) }
	}

	override fun toList(): List<Pair<String, Any?>> {
		return listOf(key to value)
	}

	override fun toMap(): Map<String, Any?> {
		return mapOf(key to value)
	}
}