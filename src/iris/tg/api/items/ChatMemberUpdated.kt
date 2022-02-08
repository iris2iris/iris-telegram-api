package iris.tg.api.items

interface ChatMemberUpdated {
	val chat: Chat
	val from: User
	val date: Long
	val oldChatMember: ChatMember
	val newChatMember: ChatMember
	val invite_link: ChatInviteLink?
}