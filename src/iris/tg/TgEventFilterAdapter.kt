package iris.tg

import iris.tg.event.*

open class TgEventFilterAdapter : TgEventFilter {
	override fun filterInvites(invites: List<ChatEvent>): List<ChatEvent> {
		return invites
	}

	override fun filterLeaves(leaves: List<ChatEvent>): List<ChatEvent> {
		return leaves
	}

	override fun filterMessages(messages: List<Message>): List<Message> {
		return messages
	}

	override fun filterTitleUpdates(updaters: List<TitleUpdate>): List<TitleUpdate> {
		return updaters
	}

	override fun filterCallbacks(callbacks: List<CallbackEvent>): List<CallbackEvent> {
		return callbacks
	}
}