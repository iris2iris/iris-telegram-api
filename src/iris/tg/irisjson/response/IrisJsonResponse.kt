package iris.tg.irisjson.response

import iris.json.JsonItem
import iris.tg.api.items.Error
import iris.tg.api.response.TgResponse
import iris.tg.irisjson.items.IrisJsonError
import iris.tg.irisjson.items.IrisJsonTgItem

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
abstract class IrisJsonResponse(source: JsonItem) : IrisJsonTgItem(source), TgResponse {

	override val ok: Boolean by lazyItem { source["ok"].asBooleanOrNull() ?: true }

	override val error: Error by lazyItem { if (!ok) IrisJsonError(source) else throw IllegalStateException("Response was ok") }
}