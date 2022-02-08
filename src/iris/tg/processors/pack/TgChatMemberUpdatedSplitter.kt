package iris.tg.processors.pack

import iris.tg.api.items.ChatMemberUpdated
import java.util.*

/**
 * @created 03.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TgChatMemberUpdatedSplitter<CMU: ChatMemberUpdated>(private val handler: TgChatMemberUpdatedHandler<CMU>) {

	private val ownersChanged = LinkedList<CMU>()
	private val administratorsChanged = LinkedList<CMU>()
	private val administratorsCanceled = LinkedList<CMU>()
	private val chatMembersRestricted = LinkedList<CMU>()
	private val chatMemberLeft = LinkedList<CMU>()
	private val chatMemberBanned = LinkedList<CMU>()
	private val chatMemberNew = LinkedList<CMU>()

	fun process(items: List<CMU>) {
		for (i in items) {
			val newUser = i.newChatMember
			when (newUser.status) {
				"creator" -> ownersChanged += i
				"administrator" -> administratorsChanged += i
				"restricted" -> chatMembersRestricted += i
				"left" -> chatMemberLeft += i
				"kicked" -> chatMemberBanned += i
				"new" -> chatMemberNew += i
			}
		}

		if (ownersChanged.isNotEmpty()) {
			handler.ownersChanged(ownersChanged)
			ownersChanged.clear()
		}

		if (administratorsChanged.isNotEmpty()) {
			handler.administratorsChanged(administratorsChanged)
			administratorsChanged.clear()
		}

		if (chatMembersRestricted.isNotEmpty()) {
			handler.chatMembersRestricted(chatMembersRestricted)
			chatMembersRestricted.clear()
		}

		if (chatMemberNew.isNotEmpty()) {
			handler.chatMemberNew(chatMemberNew)
			chatMemberNew.clear()
		}

		if (chatMemberLeft.isNotEmpty()) {
			handler.chatMemberLeft(chatMemberLeft)
			chatMemberLeft.clear()
		}

		if (chatMemberBanned.isNotEmpty()) {
			handler.chatMemberBanned(chatMemberBanned)
			chatMemberBanned.clear()
		}
	}



}