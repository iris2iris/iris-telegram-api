package iris.tg.event

import iris.json.JsonItem

/**
 * @created 28.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
interface Event {
	val source: JsonItem
}