package iris.tg

import iris.json.JsonItem

/**
 * @created 31.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
interface TgUpdateProcessor {
	fun processUpdates(updates: List<JsonItem>)
}