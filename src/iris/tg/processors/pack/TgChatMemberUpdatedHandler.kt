package iris.tg.processors.pack

import iris.tg.api.items.ChatMemberUpdated

/**
 * @created 03.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgChatMemberUpdatedHandler<CMU: ChatMemberUpdated> {
	fun ownersChanged(items: List<CMU>)
	fun administratorsChanged(items: List<CMU>)
	fun administratorsCanceled(items: List<CMU>)
	fun chatMembersRestricted(items: List<CMU>)
	fun chatMemberLeft(items: List<CMU>)
	fun chatMemberBanned(items: List<CMU>)
	fun chatMemberNew(items: List<CMU>)
}