package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.ChatPermissions
import iris.tg.pojo.items.ChatPermissions_Pojo

/**
 * @created 10.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class IrisJsonChatPermissions(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), ChatPermissions {
	override val can_send_messages: Boolean?
		get() = source["can_send_messages"].asBooleanOrNull()
	override val can_send_media_messages: Boolean?
		get() = source["can_send_media_messages"].asBooleanOrNull()
	override val can_send_polls: Boolean?
		get() = source["can_send_polls"].asBooleanOrNull()
	override val can_send_other_messages: Boolean?
		get() = source["can_send_other_messages"].asBooleanOrNull()
	override val can_add_web_page_previews: Boolean?
		get() = source["can_add_web_page_previews"].asBooleanOrNull()
	override val can_change_info: Boolean?
		get() = source["can_change_info"].asBooleanOrNull()
	override val can_invite_users: Boolean?
		get() = source["can_invite_users"].asBooleanOrNull()
	override val can_pin_messages: Boolean?
		get() = source["can_pin_messages"].asBooleanOrNull()

	override fun pojo(): ChatPermissions {
		return ChatPermissions_Pojo(can_send_messages, can_send_media_messages, can_send_polls, can_send_other_messages, can_add_web_page_previews, can_change_info, can_invite_users, can_pin_messages)
	}
}