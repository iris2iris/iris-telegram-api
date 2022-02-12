package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.*

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonChat(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Chat {
	override val id: Long by lazyItem() { source["id"].asLong() }
	override val type: String by lazyItem() { source["type"].asString() }
	override val title: String? by lazyItemOrNull("title") { it.asString() }
	override val username: String? by lazyItemOrNull("username") { it.asString() }
	override val firstName: String? by lazyItemOrNull("first_name") { it.asString() }
	override val lastName: String? by lazyItemOrNull("last_name") { it.asString() }
	override val photo: ChatPhoto? = null
	override val bio: String? by lazyItemOrNull("bio") { it.asString() }
	override val hasPrivateForwards: Boolean by lazyItem() { sourceItem["has_private_forwards"].asBooleanOrNull() ?: false }
	override val description: String? by lazyItemOrNull("description") { it.asString() }
	override val inviteLink: String? by lazyItemOrNull("inviteLink") { it.asString() }
	override val pinnedMessage: Message? by lazyItemOrNull("pinned_message") { IrisJsonMessage(it) }
	override val permissions: ChatPermissions?
		get() = itemOrNull(source["permissions"]) { IrisJsonChatPermissions(it) }

	override val slowModeDelay: Int
		get() = source["permissions"].asIntOrNull() ?: 0
	override val messageAutoDeleteTime: Int
		get() = source["message_auto_delete_time"].asIntOrNull() ?: 0
	override val hasProtectedContent: Boolean
		get() = source["has_protected_content"].asBooleanOrNull() ?: false
	override val stickerSetName: String?
		get() = source["sticker_set_name"].asStringOrNull()
	override val canSetStickerSet: Boolean
		get() = source["can_set_sticker_set"].asBooleanOrNull() ?: false
	override val linkedChatId: Long
		get() = source["linked_chat_id"].asLongOrNull() ?: 0L
	override val location: ChatLocation?
		get() = itemOrNull(source["location"]) { IrisJsonChatLocation(it) }

}