package iris.tg.connection.query

import iris.tg.connection.StringBuilderUrlEncoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MapQuery(val map: Map<String, Any?>): Query {

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
		if (map.isEmpty()) return
		for ((key, value ) in map) {
			encode(sb, key)
			sb.append('=')
			value?.apply { encode(sb, this.toString()) }
			sb.append("&")
		}
	}

	override fun toList(): List<Pair<String, Any?>> {
		return map.toList()
	}

	override fun toMap(): Map<String, Any?> {
		return map
	}

}