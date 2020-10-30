package iris.tg.event.group

import iris.json.JsonItem
import iris.tg.event.CallbackEvent

/**
 * @created 28.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
class GroupCallbackEvent(override val source: JsonItem) : CallbackEvent {
	override val id: String by lazy { source["id"].asString() }
	override val fromId: Long by lazy { source["from"]["id"].asLong() }
	override val data: String by lazy { source["data"].asString() }
	override val peerId: Long by lazy { source["chat"]["id"].asLong() }
	override val conversationMessageId: Int by lazy { source["conversation_message_id"].asInt() }
}