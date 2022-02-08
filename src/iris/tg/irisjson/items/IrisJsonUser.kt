package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.User

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonUser(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), User {

	override val id: Long by lazyItem { source["id"].asLong() }

	override val isBot: Boolean by lazyItem { source["is_bot"].asBooleanOrNull() ?: false }

	override val firstName: String by lazyItem { source["first_name"].asString() }

	override val lastName: String? by lazyItem { source["last_name"].asStringOrNull() }

	override val username: String? by lazyItem { source["username"].asStringOrNull() }

	override val languageCode: String?
		get() = source["language_code"].asStringOrNull()

	override val canJoinGroups: Boolean
		get() = source["can_join_groups"].asBooleanOrNull() ?: false

	override val canReadAllGroupMessages: Boolean
		get() = source["can_read_all_group_messages"].asBooleanOrNull() ?: false

	override val supportsInlineQueries: Boolean
		get() = source["supports_inline_queries"].asBooleanOrNull() ?: false
}