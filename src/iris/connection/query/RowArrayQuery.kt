package iris.connection.query

import iris.connection.StringBuilderUrlEncoder
import java.nio.charset.StandardCharsets
import kotlin.math.max

open class RowArrayQuery(initialCapacity: Int = DEFAULT_INITIAL_CAPACITY) : MutableQuery {

	companion object {
		const val DEFAULT_INITIAL_CAPACITY = 8
	}

	private var keys: Array<Any?> = arrayOfNulls(initialCapacity*2)
	private var pointer = 0

	constructor(initialCapacity: Int, vararg pairs: Pair<String, Any?>) : this(max(initialCapacity, pairs.size)) {
		for ((key, value) in pairs) {
			keys[pointer] = key
			pointer++
			keys[pointer] = value
			pointer++
		}
	}

	constructor(vararg pairs: Pair<String, Any?>) : this(max(pairs.size, DEFAULT_INITIAL_CAPACITY)) {
		for ((key, value) in pairs) {
			keys[pointer] = key
			pointer++
			keys[pointer] = value
			pointer++
		}
	}

	private fun ensureCapacity(minCapacity: Int) {
		val minCapacity = minCapacity*2
		if (minCapacity > keys.size) {
			val oldCapacity: Int = keys.size
			val newCapacity = newLength(
				oldCapacity,
				minCapacity - oldCapacity,  /* minimum growth */
				oldCapacity shr 1 /* preferred growth */
			)
			keys = keys.copyOf(newCapacity)
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
		// TODO: нужна функция со StringBuilder
		StringBuilderUrlEncoder.encode(sb, o, StandardCharsets.UTF_8)
	}

	private fun encodeOptions(sb: StringBuilder) {
		if (pointer == 0) return
		for (i in 0 until pointer step 2) {
			val key = keys[i]
			val value = keys[i + 1]
			encode(sb, key as String)
			sb.append('=')
			value?.apply { encode(sb, this.toString()) }
			sb.append("&")
		}
	}

	final override fun set(key: String, value: Any?) {
		ensureCapacity(pointer + 2)
		keys[pointer] = key
		pointer++
		keys[pointer] = value
		pointer++
	}

	override fun toList(): List<Pair<String, Any?>> {
		val res = ArrayList<Pair<String, Any?>>(keys.size/2)
		for (i in 0 until pointer step 2)
			res += (keys[i] as String) to keys[i+1]
		return res
	}

	override fun toMap(): Map<String, Any?> {
		val res = HashMap<String, Any?>(keys.size/2)
		for (i in 0 until pointer step 2)
			res[keys[i] as String] = keys[i+1]
		return res
	}
}