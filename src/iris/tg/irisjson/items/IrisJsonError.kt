package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.Error

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonError(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Error {
	override val errorCode: Int = source["error_code"].asInt()
	override val description: String = source["description"].asString()
}