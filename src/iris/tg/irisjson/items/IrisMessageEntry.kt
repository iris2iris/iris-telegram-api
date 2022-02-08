package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.MessageEntity
import iris.tg.api.items.User

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisMessageEntry(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), MessageEntity {
	override val type: String get() = source["type"].asString()

	override val offset: Int get() = source["offset"].asInt()
	override val length: Int get() = source["length"].asInt()
	override val url: String? get() = source["url"].asStringOrNull()

	override val user: User? get() = with(source["user"]) { if (this.isNull()) null else IrisJsonUser(this) }

	override val language: String? get() = source["url"].asStringOrNull()
}