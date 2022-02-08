package iris.tg.irisjson.response

import iris.json.JsonItem
import iris.tg.irisjson.items.IrisJsonTgItem

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonResponseDefault(source: JsonItem) : IrisJsonResponse(source) {
	override val result: IrisJsonTgItem by lazyItem { IrisJsonTgItem(this.source["result"]) }
}