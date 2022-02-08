@file:Suppress("NAME_SHADOWING")

package iris.tg.processors.pack

import iris.tg.api.items.*


/**
 * @created 20.09.2019
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TgEventMessagePackFilterHandler<M : Message
		, IQ : InlineQuery
		, CIR : ChosenInlineResult
		, CQ : CallbackQuery
		, SQ : ShippingQuery
		, PCQ: PreCheckoutQuery
		, P: Poll
		, PA: PollAnswer
		, CMU: ChatMemberUpdated
		, CJR: ChatJoinRequest
>(private val filters: Array<TgEventMessagePackFilter<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>>, private val handler: TgEventMessagePackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>) :
	TgEventMessagePackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR> {

	private val splitter by lazy(LazyThreadSafetyMode.NONE) { TgEventPackSplitMessageHandler(this) }

	override fun messages(messages: List<M>) {
		splitter.messages(messages)
	}

	override fun editedMessages(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.editedMessages(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.editedMessages(messages)
	}

	override fun channelPosts(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.channelPosts(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.channelPosts(messages)
	}

	override fun editedChannelPosts(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.editedChannelPosts(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.editedChannelPosts(messages)
	}

	override fun inlineQueries(items: List<IQ>) {
		var items = items
		for (i in filters) {
			items = i.inlineQueries(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.inlineQueries(items)
	}

	override fun chosenInlineResults(items: List<CIR>) {
		var items = items
		for (i in filters) {
			items = i.chosenInlineResults(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.chosenInlineResults(items)
	}

	override fun callbackQueries(items: List<CQ>) {
		var items = items
		for (i in filters) {
			items = i.callbackQueries(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.callbackQueries(items)
	}

	override fun shippingQueries(items: List<SQ>) {
		var items = items
		for (i in filters) {
			items = i.shippingQueries(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.shippingQueries(items)
	}

	override fun preCheckoutQueries(items: List<PCQ>) {
		var items = items
		for (i in filters) {
			items = i.preCheckoutQueries(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.preCheckoutQueries(items)
	}

	override fun polls(items: List<P>) {
		var items = items
		for (i in filters) {
			items = i.polls(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.polls(items)
	}

	override fun pollAnswers(items: List<PA>) {
		var items = items
		for (i in filters) {
			items = i.pollAnswers(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.pollAnswers(items)
	}

	override fun myChatMembers(items: List<CMU>) {
		var items = items
		for (i in filters) {
			items = i.myChatMembers(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.myChatMembers(items)
	}

	override fun chatMembers(items: List<CMU>) {
		var items = items
		for (i in filters) {
			items = i.chatMembers(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.chatMembers(items)
	}

	override fun chatJoinRequests(items: List<CJR>) {
		var items = items
		for (i in filters) {
			items = i.chatJoinRequests(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.chatJoinRequests(items)
	}

	//////////////

	override fun texts(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.texts(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.texts(messages)
	}

	override fun newChatMembers(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.newChatMembers(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.newChatMembers(messages)
	}

	override fun leftChatMember(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.leftChatMember(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.leftChatMember(messages)
	}

	override fun newChatTitle(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.newChatTitle(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.newChatTitle(messages)
	}

	override fun newChatPhoto(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.newChatPhoto(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.newChatPhoto(messages)
	}

	override fun deleteChatPhoto(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.deleteChatPhoto(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.deleteChatPhoto(messages)
	}

	override fun groupChatCreated(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.groupChatCreated(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.groupChatCreated(messages)
	}

	override fun supergroupChatCreated(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.supergroupChatCreated(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.supergroupChatCreated(messages)
	}

	override fun channelChatCreated(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.channelChatCreated(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.channelChatCreated(messages)
	}

	override fun messageAutoDeleteTimerChanged(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.messageAutoDeleteTimerChanged(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.messageAutoDeleteTimerChanged(messages)
	}

	override fun migrateToChatId(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.migrateToChatId(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.migrateToChatId(messages)
	}

	override fun migrateFromChatId(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.migrateFromChatId(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.migrateFromChatId(messages)
	}

	override fun pinnedMessage(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.pinnedMessage(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.pinnedMessage(messages)
	}

	override fun invoice(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.invoice(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.invoice(messages)
	}

	override fun successfulPayment(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.successfulPayment(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.successfulPayment(messages)
	}

	override fun connectedWebsite(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.connectedWebsite(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.connectedWebsite(messages)
	}

	override fun passportData(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.passportData(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.passportData(messages)
	}

	override fun proximityAlertTriggered(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.proximityAlertTriggered(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.proximityAlertTriggered(messages)
	}

	override fun voiceChatScheduled(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.voiceChatScheduled(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.voiceChatScheduled(messages)
	}

	override fun voiceChatStarted(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.voiceChatStarted(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.voiceChatStarted(messages)
	}

	override fun voiceChatEnded(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.voiceChatEnded(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.voiceChatEnded(messages)
	}

	override fun voiceChatParticipantsInvited(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.voiceChatParticipantsInvited(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.voiceChatParticipantsInvited(messages)
	}

	override fun unresolvedMessages(messages: List<M>) {
		var messages = messages
		for (i in filters) {
			messages = i.unresolvedMessages(messages)
			if (messages.isEmpty())
				return
		}
		if (messages.isNotEmpty())
			handler.unresolvedMessages(messages)
	}

	/////////////

	override fun ownersChanged(items: List<CMU>) {
		var items = items
		for (i in filters) {
			items = i.ownersChanged(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.ownersChanged(items)
	}

	override fun administratorsChanged(items: List<CMU>) {
		var items = items
		for (i in filters) {
			items = i.administratorsChanged(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.administratorsChanged(items)
	}

	override fun administratorsCanceled(items: List<CMU>) {
		var items = items
		for (i in filters) {
			items = i.administratorsCanceled(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.administratorsCanceled(items)
	}

	override fun chatMembersRestricted(items: List<CMU>) {
		var items = items
		for (i in filters) {
			items = i.chatMembersRestricted(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.chatMembersRestricted(items)
	}

	override fun chatMemberLeft(items: List<CMU>) {
		var items = items
		for (i in filters) {
			items = i.chatMemberLeft(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.chatMemberLeft(items)
	}

	override fun chatMemberBanned(items: List<CMU>) {
		var items = items
		for (i in filters) {
			items = i.chatMemberBanned(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.chatMemberBanned(items)
	}

	override fun chatMemberNew(items: List<CMU>) {
		var items = items
		for (i in filters) {
			items = i.chatMemberNew(items)
			if (items.isEmpty())
				return
		}
		if (items.isNotEmpty())
			handler.chatMemberNew(items)
	}
}