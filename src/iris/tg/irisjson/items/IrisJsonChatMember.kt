package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.*

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
abstract class IrisJsonChatMember(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), ChatMember {
	override val status: String by lazyItem { source["status"].asString() }
	override val user: User by lazyItem { IrisJsonUser(source["user"]) }
}

open class IrisJsonChatMemberOwner(sourceItem: JsonItem) : IrisJsonChatMember(sourceItem), ChatMemberOwner {
	override val isAnonymous: Boolean by lazyItem() { source["is_anonymous"].asBooleanOrNull() ?: false }
	override val customTitle: String? by lazyItemOrNull("custom_title") { it.asStringOrNull() }
}

open class IrisJsonChatMemberAdministrator(sourceItem: JsonItem) : IrisJsonChatMember(sourceItem), ChatMemberAdministrator {
	override val canBeEdited: Boolean by lazyItem { source["can_be_edited"].asBooleanOrNull() ?: false }
	override val isAnonymous: Boolean by lazyItem { source["is_anonymous"].asBooleanOrNull() ?: false }
	override val canManageChat: Boolean by lazyItem { source["can_manage_chat"].asBooleanOrNull() ?: false }
	override val canDeleteMessages: Boolean by lazyItem { source["can_delete_messages"].asBooleanOrNull() ?: false }
	override val canManageVoiceChats: Boolean by lazyItem { source["can_manage_voice_chats"].asBooleanOrNull() ?: false }
	override val canRestrictMembers: Boolean by lazyItem { source["can_restrict_members"].asBooleanOrNull() ?: false }
	override val canPromoteMembers: Boolean by lazyItem { source["can_promote_members"].asBooleanOrNull() ?: false }
	override val canChangeInfo: Boolean by lazyItem { source["can_change_info"].asBooleanOrNull() ?: false }
	override val canInviteUsers: Boolean by lazyItem { source["can_invite_users"].asBooleanOrNull() ?: false }
	override val canPostMessages: Boolean by lazyItem { source["can_post_messages"].asBooleanOrNull() ?: false }
	override val canEditMessages: Boolean by lazyItem { source["can_edit_messages"].asBooleanOrNull() ?: false }
	override val canPinMessages: Boolean by lazyItem { source["can_pin_messages"].asBooleanOrNull() ?: false }
	override val customTitle: Boolean by lazyItem { source["custom_title"].asBooleanOrNull() ?: false }
}

open class IrisJsonChatMemberMember(sourceItem: JsonItem) : IrisJsonChatMember(sourceItem), ChatMemberMember {

}

open class IrisJsonChatMemberRestricted(sourceItem: JsonItem) : IrisJsonChatMember(sourceItem), ChatMemberRestricted {
	override val isMember: Boolean by lazyItem { source["is_member"].asBooleanOrNull() ?: false }
	override val canChangeInfo: Boolean by lazyItem { source["can_change_info"].asBooleanOrNull() ?: false }
	override val canInviteUsers: Boolean by lazyItem { source["can_invite_users"].asBooleanOrNull() ?: false }
	override val canPinMessages: Boolean by lazyItem { source["can_pin_messages"].asBooleanOrNull() ?: false }
	override val canSendMessages: Boolean by lazyItem { source["can_send_messages"].asBooleanOrNull() ?: false }
	override val canSendMediaMessages: Boolean by lazyItem { source["can_send_media_messages"].asBooleanOrNull() ?: false }
	override val canSendPolls: Boolean by lazyItem { source["can_send_polls"].asBooleanOrNull() ?: false }
	override val canSendOtherMessages: Boolean by lazyItem { source["can_send_other_messages"].asBooleanOrNull() ?: false }
	override val canAddWebPagePreviews: Boolean by lazyItem { source["can_add_web_page_previews"].asBooleanOrNull() ?: false }
	override val untilDate: Long by lazyItem { source["until_date"].asLong() }
}

open class IrisJsonChatMemberLeft(sourceItem: JsonItem) : IrisJsonChatMember(sourceItem), ChatMemberLeft {

}

open class IrisJsonChatMemberBanned(sourceItem: JsonItem) : IrisJsonChatMember(sourceItem), ChatMemberBanned {
	override val untilDate: Long by lazyItem { source["until_date"].asLong() }
}

