package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.*

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonChatMemberUpdated(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), ChatMemberUpdated {

	override val chat: Chat by lazyItem { IrisJsonChat(source["chat"]) }

	override val from: User by lazyItem { IrisJsonUser(source["user"]) }

	override val date: Long get() = source["date"].asLongOrNull() ?: 0L

	override val oldChatMember: ChatMember by lazyItem { IrisJsonChatMemberFactory.create(source["old_chat_member"]) }

	override val newChatMember: ChatMember by lazyItem { IrisJsonChatMemberFactory.create(source["new_chat_member"]) }

	override val invite_link: ChatInviteLink?
		get() = TODO("Not yet implemented")
}