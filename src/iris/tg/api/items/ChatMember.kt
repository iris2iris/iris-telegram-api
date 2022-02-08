package iris.tg.api.items

import iris.json.JsonItem
import iris.tg.irisjson.items.*

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface ChatMember {
	val status: String
	val user: User
}

interface ChatMemberOwner : ChatMember {
	val isAnonymous: Boolean
	val customTitle: String?
}

interface ChatMemberAdministrator : ChatMember {
	val canBeEdited: Boolean
	val isAnonymous: Boolean
	val canManageChat: Boolean
	val canDeleteMessages: Boolean
	val canManageVoiceChats: Boolean
	val canRestrictMembers: Boolean
	val canPromoteMembers: Boolean
	val canChangeInfo: Boolean
	val canInviteUsers: Boolean
	val canPostMessages: Boolean
	val canEditMessages: Boolean
	val canPinMessages: Boolean
	val customTitle: Boolean
}

interface ChatMemberMember : ChatMember {

}

interface ChatMemberRestricted: ChatMember {
	val isMember: Boolean
	val canChangeInfo: Boolean
	val canInviteUsers: Boolean
	val canPinMessages: Boolean
	val canSendMessages: Boolean
	val canSendMediaMessages: Boolean
	val canSendPolls: Boolean
	val canSendOtherMessages: Boolean
	val canAddWebPagePreviews: Boolean
	val untilDate: Long
}

interface ChatMemberLeft : ChatMember {

}

interface ChatMemberBanned: ChatMember {
	val untilDate: Long
}

object IrisJsonChatMemberFactory {
	fun create(it: JsonItem): IrisJsonChatMember = when (it["status"].asString()) {
		"creator" -> IrisJsonChatMemberOwner(it)
		"administrator" -> IrisJsonChatMemberAdministrator(it)
		"member" -> IrisJsonChatMemberMember(it)
		"restricted" -> IrisJsonChatMemberRestricted(it)
		"left" -> IrisJsonChatMemberLeft(it)
		"kicked" -> IrisJsonChatMemberBanned(it)
		else -> throw IllegalArgumentException("")
	}
}