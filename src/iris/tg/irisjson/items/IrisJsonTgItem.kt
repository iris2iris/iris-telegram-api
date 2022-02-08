package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.TgItem

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonTgItem(val source: JsonItem) : TgItem {

	protected inline fun <T>lazyItemOrNull(field: String, crossinline factory: (item: JsonItem) -> T): Lazy<T?> {
		return lazy(LazyThreadSafetyMode.NONE) {
			itemOrNull(source[field], factory)
		}
	}

	protected inline fun <T>itemOrNull(item: JsonItem, factory: (item: JsonItem) -> T): T? {
		return if (item.isNull()) null else factory(item)
	}

	protected inline fun <T>lazyItem(crossinline factory: () -> T): Lazy<T> {
		return lazy(LazyThreadSafetyMode.NONE) { factory() }
	}

	override fun toString(): String {
		return source.toJsonString()
	}
}