package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.Contact

open class IrisJsonContact(sourceItem: JsonItem): IrisJsonTgItem(sourceItem), Contact {
	override val phone_number: String
		get() = source["phone_number"].asString()
	override val first_name: String
		get() = source["first_name"].asString()
	override val last_name: String?
		get() = source["last_name"].asStringOrNull()
	override val user_id: Long
		get() = source["user_id"].asLongOrNull() ?: 0L
	override val vcard: String?
		get() = source["vcard"].asStringOrNull()
}
