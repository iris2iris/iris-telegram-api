package iris.tg.irisjson.response

import iris.json.JsonItem
import iris.tg.api.response.BooleanResponse

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonBooleanResponse(source: JsonItem) : IrisJsonResponse(source), BooleanResponse {
	override val result: Boolean
		get() = this.source["result"].asBooleanOrNull() ?: false
}