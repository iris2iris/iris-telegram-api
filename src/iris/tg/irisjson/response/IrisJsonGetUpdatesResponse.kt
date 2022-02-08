package iris.tg.irisjson.response

import iris.json.JsonItem
import iris.tg.api.items.Update
import iris.tg.api.response.GetUpdatesResponse
import iris.tg.irisjson.items.IrisJsonUpdate

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonGetUpdatesResponse(sourceItem: JsonItem) : IrisJsonResponse(sourceItem), GetUpdatesResponse {
	override val result: List<Update>? by lazyItemOrNull("result") { it.iterable().map { IrisJsonUpdate(it) } }
}