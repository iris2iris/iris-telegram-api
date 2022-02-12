package iris.tg.pojo.items

import iris.tg.api.items.ChatPermissions

/**
 * @created 10.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class ChatPermissions_Pojo(
	override val can_send_messages: Boolean?,
	override val can_send_media_messages: Boolean?,
	override val can_send_polls: Boolean?,
	override val can_send_other_messages: Boolean?,
	override val can_add_web_page_previews: Boolean?,
	override val can_change_info: Boolean?,
	override val can_invite_users: Boolean?,
	override val can_pin_messages: Boolean?,
): ChatPermissions