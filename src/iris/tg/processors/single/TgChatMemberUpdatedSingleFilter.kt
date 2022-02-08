package iris.tg.processors.single

import iris.tg.api.items.ChatMemberUpdated

/**
 * @created 03.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgChatMemberUpdatedSingleFilter<CMU : ChatMemberUpdated> {
	fun ownerChanged(item: CMU): CMU?
	fun administratorChanged(item: CMU): CMU?
	fun administratorCanceled(item: CMU): CMU?
	fun chatMemberRestricted(item: CMU): CMU?
	fun chatMemberLeft(item: CMU): CMU?
	fun chatMemberBanned(item: CMU): CMU?
	fun chatMemberNew(item: CMU): CMU?
}