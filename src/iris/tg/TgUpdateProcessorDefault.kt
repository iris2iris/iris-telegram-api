package iris.tg

import iris.json.JsonArray
import iris.json.JsonItem
import iris.tg.event.group.*

/**
 * @created 31.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
class TgUpdateProcessorDefault(private val eventHandler: TgEventHandler) : TgUpdateProcessor {

	override fun processUpdates(updates: List<JsonItem>) {

		var checkMessages: MutableList<GroupMessage>? = null
		var checkInvites: MutableList<GroupChatEvent>? = null
		val titleUpdaters: MutableList<GroupTitleUpdate>? = null
		val pinUpdaters: MutableList<GroupPinUpdate>? = null
		var checkLeave: MutableList<GroupChatEvent>? = null
		var callbackEvents: MutableList<GroupCallbackEvent>? = null


		for (msg in updates) {
			var m: JsonItem
			if (msg["message"].also { m = it }.isNotNull()) { // is message
				var type: JsonItem
				when {
					m["new_chat_member"].also { type = it }.isNotNull() -> {
						val users = if (type.isArray()) {
							(type as JsonArray).getList()
						} else
							listOf(m["new_chat_member"])
						for (u in users) {
							if (checkInvites == null) checkInvites = mutableListOf()
							checkInvites.add(GroupChatEvent(m, u))
						}
					}
					m["left_chat_member"].also { type = it }.isNotNull() -> {
						val u = type
						if (checkLeave == null) checkLeave = mutableListOf()
						checkLeave.add(GroupChatEvent(m, u))
					}
					else -> {
						if (checkMessages == null) checkMessages = mutableListOf()
						checkMessages.add(GroupMessage(m, m))
					}
				}
			} else if (msg["callback_query"].also{ m = it }.isNotNull()) {
				if (callbackEvents == null) callbackEvents = mutableListOf()
				callbackEvents.add(GroupCallbackEvent(m))
			}
		}

		if (checkMessages != null)
			processMessages(checkMessages)
		if (checkInvites != null)
			processInvites(checkInvites)
		if (checkLeave != null)
			processLeaves(checkLeave)
		if (callbackEvents != null)
			processCallbackQueries(callbackEvents)
	}

	private fun processMessages(messages: List<GroupMessage>) {
		eventHandler.processMessages(messages)
	}

	private fun processEditMessages(messages: List<GroupMessage>) {
		eventHandler.processEditedMessages(messages)
	}

	private fun processInvites(invites: List<GroupChatEvent>) {
		eventHandler.processInvites(invites)
	}

	private fun processTitleUpdates(updaters: List<GroupTitleUpdate>) {
		eventHandler.processTitleUpdates(updaters)
	}

	private fun processPinUpdates(updaters: List<GroupPinUpdate>) {
		eventHandler.processPinUpdates(updaters)
	}

	private fun processLeaves(users: List<GroupChatEvent>) {
		eventHandler.processLeaves(users)
	}

	private fun processCallbackQueries(queries: List<GroupCallbackEvent>) {
		eventHandler.processCallbacks(queries)
	}
}