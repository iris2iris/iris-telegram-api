package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.Invoice

open class IrisJsonInvoice(it: JsonItem): IrisJsonTgItem(it), Invoice {
	override val title: String by lazyItem { source["title"].asString() }
	override val description: String by lazyItem { source["description"].asString() }

	override val start_parameter: String by lazyItem { source["start_parameter"].asString() }
	override val currency: String by lazyItem { source["currency"].asString() }
	override val total_amount: Int by lazyItem { source["total_amount"].asInt() }
}
