package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.SuccessfulPayment

open class IrisJsonSuccessfulPayment(it: JsonItem): IrisJsonTgItem(it), SuccessfulPayment {
	override val currency: String
		get() = source["currency"].asString()
	override val total_amount: Int
		get() = source["total_amount"].asInt()
}
