package iris.tg.processors.single

import iris.tg.api.items.ChatMemberUpdated

/**
 * @created 03.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgChatMemberUpdatedSingleHandler<CMU: ChatMemberUpdated> {
	fun ownerChanged(item: CMU)
	fun administratorChanged(item: CMU)
	fun administratorCanceled(item: CMU)
	fun chatMemberRestricted(item: CMU)
	fun chatMemberLeft(item: CMU)
	fun chatMemberBanned(item: CMU)
	fun chatMemberNew(item: CMU)
}