package iris.tg

import iris.json.JsonItem

/**
 * @created 02.12.2019
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
interface TgRetrievable {
	fun retrieve(wait: Boolean = true): List<JsonItem>
}