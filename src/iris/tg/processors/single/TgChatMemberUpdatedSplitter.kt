package iris.tg.processors.single

import iris.tg.api.items.ChatMemberUpdated
import iris.tg.api.items.Message

/**
 * @created 03.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TgChatMemberUpdatedSplitter<CMU: ChatMemberUpdated>(private val handler: TgChatMemberUpdatedSingleHandler<CMU>) {

	fun process(item: CMU) {
		val newUser = item.newChatMember
		when (newUser.status) {
			"creator" -> handler.ownerChanged(item)
			"administrator" -> handler.administratorChanged(item)
			"restricted" -> handler.chatMemberRestricted(item)
			"left" -> handler.chatMemberLeft(item)
			"kicked" -> handler.chatMemberBanned(item)
			"new" -> handler.chatMemberNew(item)
		}
	}
}