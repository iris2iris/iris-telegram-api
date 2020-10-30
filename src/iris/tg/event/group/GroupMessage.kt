package iris.tg.event.group

import iris.json.JsonArray
import iris.json.JsonItem
import iris.json.JsonObject
import iris.tg.event.Message
import kotlin.LazyThreadSafetyMode.NONE

/**
 * @created 27.09.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
open class GroupMessage(source: JsonItem, userIdSource: JsonItem) : GroupChatEvent(source, userIdSource), Message {

	override val text by lazy(NONE) { source["text"].asStringOrNull()?.replace("\r", "") }
	override val attachments: List<JsonItem>? by lazy(NONE) { val res = source["attachments"]; (if (res.isNull()) null else res as JsonArray)?.getList() }
	override val forwardedMessages: List<JsonItem>? by lazy(NONE) { (source["fwd_messages"] as? JsonArray)?.getList() }
	override val replyMessage: JsonObject? by lazy(NONE) { source["reply_message"] as? JsonObject }

}