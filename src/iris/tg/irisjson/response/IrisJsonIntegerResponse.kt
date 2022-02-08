package iris.tg.irisjson.response

import iris.json.JsonItem
import iris.tg.api.response.IntegerResponse

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonIntegerResponse(source: JsonItem) : IrisJsonResponse(source), IntegerResponse {
	override val result: Int = this.source["result"].asIntOrNull() ?: 0
}