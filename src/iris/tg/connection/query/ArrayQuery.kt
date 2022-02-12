package iris.tg.connection.query

import iris.tg.connection.StringBuilderUrlEncoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.math.max

open class ArrayQuery(initialCapacity: Int = 16) : MutableQuery {

	private var keys: Array<String?> = arrayOfNulls(initialCapacity)
	private var values: Array<Any?> = arrayOfNulls(initialCapacity)
	private var pointer = 0

	constructor(vararg pairs: Pair<String, Any?>) : this(pairs.size) {
		ensureCapacity(pairs.size)
		for ((key, value) in pairs) {
			keys[pointer] = key
			values[pointer] = value
			pointer++
		}
	}

	private fun ensureCapacity(minCapacity: Int) {
		if (minCapacity > keys.size) {
			val oldCapacity: Int = keys.size
			val newCapacity = newLength(
				oldCapacity,
				minCapacity - oldCapacity,  /* minimum growth */
				oldCapacity shr 1 /* preferred growth */
			)
			keys = keys.copyOf(newCapacity)
			values = values.copyOf(newCapacity)
		}
	}

	private fun newLength(oldLength: Int, minGrowth: Int, prefGrowth: Int): Int {
		val newLength = max(minGrowth, prefGrowth) + oldLength
		return if (newLength - Int.MAX_VALUE - 8 <= 0) {
			newLength
		} else throw OutOfMemoryError()
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
		if (pointer == 0) return
		for (i in 0 until pointer) {
			val key = keys[i]!!
			val value = values[i]
			encode(sb, key)
			sb.append('=')
			value?.apply { encode(sb, this.toString()) }
			sb.append("&")
		}
	}

	override fun set(key: String, value: Any?) {
		ensureCapacity(pointer + 1)
		keys[pointer] = key
		values[pointer] = value
		pointer++
	}

	override fun toList(): List<Pair<String, Any?>> {
		val res = ArrayList<Pair<String, Any?>>(keys.size)
		for (i in 0 until pointer) {
			val key = keys[i]!!
			val value = values[i]
			res += key to value
		}
		return res
	}

	override fun toMap(): Map<String, Any?> {
		val res = HashMap<String, Any?>(keys.size)
		for (i in 0 until pointer) {
			val key = keys[i]!!
			val value = values[i]
			res[key] = value
		}
		return res
	}
}