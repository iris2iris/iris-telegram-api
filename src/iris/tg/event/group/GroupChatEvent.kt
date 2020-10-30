package iris.tg.event.group

import iris.json.JsonItem
import iris.tg.event.ChatEvent
import kotlin.LazyThreadSafetyMode.NONE

/**
 * @created 28.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
open class GroupChatEvent(source: JsonItem, private val userIdSource: JsonItem) : ChatEvent {

	override val source: JsonItem = source
	//private val message = source// by lazy(NONE) { source["message"] }
	override val id: Long by lazy(NONE) { source["id"].asLong() }
	override val fromId: Long by lazy(NONE) { source["from"]["id"].asLong() }
	override val userId: Long by lazy(NONE) { userIdSource["id"].asLong() }
	override val chatId: Long by lazy(NONE) { if (source["chat"]["type"].asString() == "private") 0L else peerId }
	override val peerId: Long by lazy(NONE) { source["chat"]["id"].asLong() }
	override val conversationMessageId: Int by lazy(NONE) { source["conversation_message_id"].asInt() }
	override val date: Long by lazy(NONE) { source["date"].asLong() }
}