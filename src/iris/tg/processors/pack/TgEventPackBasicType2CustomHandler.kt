package iris.tg.processors.pack

import iris.tg.api.items.*

/**
 * @created 08.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TgEventPackBasicType2CustomHandler<M : Message
		, IQ : InlineQuery
		, CIR : ChosenInlineResult
		, CQ : CallbackQuery
		, SQ : ShippingQuery
		, PCQ: PreCheckoutQuery
		, P: Poll
		, PA: PollAnswer
		, CMU: ChatMemberUpdated
		, CJR: ChatJoinRequest
>(private val handler: TgEventMessagePackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>) : TgEventMessagePackHandlerBasicTypes {

	private fun <T>Any.cast() = this as T

	override fun ownersChanged(items: List<ChatMemberUpdated>) {
		handler.ownersChanged(items.cast())
	}

	override fun administratorsChanged(items: List<ChatMemberUpdated>) {
		handler.administratorsChanged(items.cast())
	}

	override fun administratorsCanceled(items: List<ChatMemberUpdated>) {
		handler.administratorsCanceled(items.cast())
	}

	override fun chatMembersRestricted(items: List<ChatMemberUpdated>) {
		handler.chatMembersRestricted(items.cast())
	}

	override fun chatMemberLeft(items: List<ChatMemberUpdated>) {
		handler.chatMemberLeft(items.cast())
	}

	override fun chatMemberBanned(items: List<ChatMemberUpdated>) {
		handler.chatMemberBanned(items.cast())
	}

	override fun chatMemberNew(items: List<ChatMemberUpdated>) {
		handler.chatMemberNew(items.cast())
	}

	override fun messages(messages: List<Message>) {
		handler.messages(messages.cast())
	}

	override fun editedMessages(messages: List<Message>) {
		handler.editedMessages(messages.cast())
	}

	override fun channelPosts(messages: List<Message>) {
		handler.channelPosts(messages.cast())
	}

	override fun editedChannelPosts(messages: List<Message>) {
		handler.editedChannelPosts(messages.cast())
	}

	override fun inlineQueries(items: List<InlineQuery>) {
		handler.inlineQueries(items.cast())
	}

	override fun chosenInlineResults(items: List<ChosenInlineResult>) {
		handler.chosenInlineResults(items.cast())
	}

	override fun callbackQueries(items: List<CallbackQuery>) {
		handler.callbackQueries(items.cast())
	}

	override fun shippingQueries(items: List<ShippingQuery>) {
		handler.shippingQueries(items.cast())
	}

	override fun preCheckoutQueries(items: List<PreCheckoutQuery>) {
		handler.preCheckoutQueries(items.cast())
	}

	override fun polls(items: List<Poll>) {
		handler.polls(items.cast())
	}

	override fun pollAnswers(items: List<PollAnswer>) {
		handler.pollAnswers(items.cast())
	}

	override fun myChatMembers(items: List<ChatMemberUpdated>) {
		handler.myChatMembers(items.cast())
	}

	override fun chatMembers(items: List<ChatMemberUpdated>) {
		handler.chatMembers(items.cast())
	}

	override fun chatJoinRequests(items: List<ChatJoinRequest>) {
		handler.chatJoinRequests(items.cast())
	}

	override fun texts(messages: List<Message>) {
		handler.texts(messages.cast())
	}

	override fun newChatMembers(messages: List<Message>) {
		handler.newChatMembers(messages.cast())
	}

	override fun leftChatMember(messages: List<Message>) {
		handler.leftChatMember(messages.cast())
	}

	override fun newChatTitle(messages: List<Message>) {
		handler.newChatTitle(messages.cast())
	}

	override fun newChatPhoto(messages: List<Message>) {
		handler.newChatPhoto(messages.cast())
	}

	override fun deleteChatPhoto(messages: List<Message>) {
		handler.deleteChatPhoto(messages.cast())
	}

	override fun groupChatCreated(messages: List<Message>) {
		handler.groupChatCreated(messages.cast())
	}

	override fun supergroupChatCreated(messages: List<Message>) {
		handler.supergroupChatCreated(messages.cast())
	}

	override fun channelChatCreated(messages: List<Message>) {
		handler.channelChatCreated(messages.cast())
	}

	override fun messageAutoDeleteTimerChanged(messages: List<Message>) {
		handler.messageAutoDeleteTimerChanged(messages.cast())
	}

	override fun migrateToChatId(messages: List<Message>) {
		handler.migrateToChatId(messages.cast())
	}

	override fun migrateFromChatId(messages: List<Message>) {
		handler.migrateFromChatId(messages.cast())
	}

	override fun pinnedMessage(messages: List<Message>) {
		handler.pinnedMessage(messages.cast())
	}

	override fun invoice(messages: List<Message>) {
		handler.invoice(messages.cast())
	}

	override fun successfulPayment(messages: List<Message>) {
		handler.successfulPayment(messages.cast())
	}

	override fun connectedWebsite(messages: List<Message>) {
		handler.connectedWebsite(messages.cast())
	}

	override fun passportData(messages: List<Message>) {
		handler.passportData(messages.cast())
	}

	override fun proximityAlertTriggered(messages: List<Message>) {
		handler.proximityAlertTriggered(messages.cast())
	}

	override fun voiceChatScheduled(messages: List<Message>) {
		handler.voiceChatScheduled(messages.cast())
	}

	override fun voiceChatStarted(messages: List<Message>) {
		handler.voiceChatStarted(messages.cast())
	}

	override fun voiceChatEnded(messages: List<Message>) {
		handler.voiceChatEnded(messages.cast())
	}

	override fun voiceChatParticipantsInvited(messages: List<Message>) {
		handler.voiceChatParticipantsInvited(messages.cast())
	}

	override fun unresolvedMessages(messages: List<Message>) {
		handler.voiceChatParticipantsInvited(messages.cast())
	}
}