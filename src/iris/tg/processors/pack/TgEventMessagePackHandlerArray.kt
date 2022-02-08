package iris.tg.processors.pack

import iris.tg.api.items.*


/**
 * @created 22.03.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TgEventMessagePackHandlerArray<M : Message
	, IQ : InlineQuery
	, CIR : ChosenInlineResult
	, CQ : CallbackQuery
	, SQ : ShippingQuery
	, PCQ: PreCheckoutQuery
	, P: Poll
	, PA: PollAnswer
	, CMU: ChatMemberUpdated
	, CJR: ChatJoinRequest
>(val list: Array<TgEventMessagePackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>>) : TgEventMessagePackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR> {

	override fun messages(messages: List<M>) {
		for (i in list)
			i.messages(messages)
	}

	override fun editedMessages(messages: List<M>) {
		for (i in list)
			i.editedMessages(messages)
	}

	override fun channelPosts(messages: List<M>) {
		for (i in list)
			i.channelPosts(messages)
	}

	override fun editedChannelPosts(messages: List<M>) {
		for (i in list)
			i.editedChannelPosts(messages)
	}

	override fun inlineQueries(items: List<IQ>) {
		for (i in list)
			i.inlineQueries(items)
	}

	override fun chosenInlineResults(items: List<CIR>) {
		for (i in list)
			i.chosenInlineResults(items)
	}

	override fun callbackQueries(items: List<CQ>) {
		for (i in list)
			i.callbackQueries(items)
	}

	override fun shippingQueries(items: List<SQ>) {
		for (i in list)
			i.shippingQueries(items)
	}

	override fun preCheckoutQueries(items: List<PCQ>) {
		for (i in list)
			i.preCheckoutQueries(items)
	}

	override fun polls(items: List<P>) {
		for (i in list)
			i.polls(items)
	}

	override fun pollAnswers(items: List<PA>) {
		for (i in list)
			i.pollAnswers(items)
	}

	override fun myChatMembers(items: List<CMU>) {
		for (i in list)
			i.myChatMembers(items)
	}

	override fun chatMembers(items: List<CMU>) {
		for (i in list)
			i.chatMembers(items)
	}

	override fun chatJoinRequests(items: List<CJR>) {
		for (i in list)
			i.chatJoinRequests(items)
	}

	////////////

	override fun texts(messages: List<M>) {
		for (i in list)
			i.texts(messages)
	}

	override fun newChatMembers(messages: List<M>) {
		for (i in list)
			i.newChatMembers(messages)
	}

	override fun leftChatMember(messages: List<M>) {
		for (i in list)
			i.leftChatMember(messages)
	}

	override fun newChatTitle(messages: List<M>) {
		for (i in list)
			i.newChatTitle(messages)
	}

	override fun newChatPhoto(messages: List<M>) {
		for (i in list)
			i.newChatPhoto(messages)
	}

	override fun deleteChatPhoto(messages: List<M>) {
		for (i in list)
			i.deleteChatPhoto(messages)
	}

	override fun groupChatCreated(messages: List<M>) {
		for (i in list)
			i.groupChatCreated(messages)
	}

	override fun supergroupChatCreated(messages: List<M>) {
		for (i in list)
			i.supergroupChatCreated(messages)
	}

	override fun channelChatCreated(messages: List<M>) {
		for (i in list)
			i.channelChatCreated(messages)
	}

	override fun messageAutoDeleteTimerChanged(messages: List<M>) {
		for (i in list)
			i.messageAutoDeleteTimerChanged(messages)
	}

	override fun migrateToChatId(messages: List<M>) {
		for (i in list)
			i.migrateToChatId(messages)
	}

	override fun migrateFromChatId(messages: List<M>) {
		for (i in list)
			i.migrateFromChatId(messages)
	}

	override fun pinnedMessage(messages: List<M>) {
		for (i in list)
			i.pinnedMessage(messages)
	}

	override fun invoice(messages: List<M>) {
		for (i in list)
			i.invoice(messages)
	}

	override fun successfulPayment(messages: List<M>) {
		for (i in list)
			i.successfulPayment(messages)
	}

	override fun connectedWebsite(messages: List<M>) {
		for (i in list)
			i.connectedWebsite(messages)
	}

	override fun passportData(messages: List<M>) {
		for (i in list)
			i.passportData(messages)
	}

	override fun proximityAlertTriggered(messages: List<M>) {
		for (i in list)
			i.proximityAlertTriggered(messages)
	}

	override fun voiceChatScheduled(messages: List<M>) {
		for (i in list)
			i.voiceChatScheduled(messages)
	}

	override fun voiceChatStarted(messages: List<M>) {
		for (i in list)
			i.voiceChatStarted(messages)
	}

	override fun voiceChatEnded(messages: List<M>) {
		for (i in list)
			i.voiceChatEnded(messages)
	}

	override fun voiceChatParticipantsInvited(messages: List<M>) {
		for (i in list)
			i.voiceChatParticipantsInvited(messages)
	}

	override fun unresolvedMessages(messages: List<M>) {
		for (i in list)
			i.unresolvedMessages(messages)
	}

	//////////////////

	override fun ownersChanged(items: List<CMU>) {
		for (i in list)
			i.ownersChanged(items)
	}

	override fun administratorsChanged(items: List<CMU>) {
		for (i in list)
			i.administratorsChanged(items)
	}

	override fun administratorsCanceled(items: List<CMU>) {
		for (i in list)
			i.administratorsCanceled(items)
	}

	override fun chatMembersRestricted(items: List<CMU>) {
		for (i in list)
			i.chatMembersRestricted(items)
	}

	override fun chatMemberLeft(items: List<CMU>) {
		for (i in list)
			i.chatMemberLeft(items)
	}

	override fun chatMemberBanned(items: List<CMU>) {
		for (i in list)
			i.chatMemberBanned(items)
	}

	override fun chatMemberNew(items: List<CMU>) {
		for (i in list)
			i.chatMemberNew(items)
	}
}