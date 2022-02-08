package iris.tg.irisjson.response

import iris.json.JsonItem
import iris.tg.api.items.File
import iris.tg.api.response.GetFileResponse
import iris.tg.irisjson.items.IrisJsonFile

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonGetFileResponse(sourceItem: JsonItem) : IrisJsonResponse(sourceItem), GetFileResponse {
	override val result: File? by lazyItemOrNull("result") { IrisJsonFile(it) }
}