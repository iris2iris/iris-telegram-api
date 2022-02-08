package iris.tg.processors.single

import iris.tg.api.items.*


/**
 * @created 22.03.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TgEventMessageSingleHandlerArray<M : Message
		, IQ : InlineQuery
		, CIR : ChosenInlineResult
		, CQ : CallbackQuery
		, SQ : ShippingQuery
		, PCQ: PreCheckoutQuery
		, P: Poll
		, PA: PollAnswer
		, CMU: ChatMemberUpdated
		, CJR: ChatJoinRequest
		>(val list: Array<TgEventMessageSingleHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>>) : TgEventMessageSingleHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR> {

	override fun message(message: M) {
		for (i in list)
			i.message(message)
	}

	override fun editedMessage(message: M) {
		for (i in list)
			i.editedMessage(message)
	}

	override fun channelPost(message: M) {
		for (i in list)
			i.channelPost(message)
	}

	override fun editedChannelPost(message: M) {
		for (i in list)
			i.editedChannelPost(message)
	}

	override fun inlineQuery(item: IQ) {
		for (i in list)
			i.inlineQuery(item)
	}

	override fun chosenInlineResult(item: CIR) {
		for (i in list)
			i.chosenInlineResult(item)
	}

	override fun callbackQuery(item: CQ) {
		for (i in list)
			i.callbackQuery(item)
	}

	override fun shippingQuery(item: SQ) {
		for (i in list)
			i.shippingQuery(item)
	}

	override fun preCheckoutQuery(item: PCQ) {
		for (i in list)
			i.preCheckoutQuery(item)
	}

	override fun poll(item: P) {
		for (i in list)
			i.poll(item)
	}

	override fun pollAnswer(item: PA) {
		for (i in list)
			i.pollAnswer(item)
	}

	override fun myChatMember(item: CMU) {
		for (i in list)
			i.myChatMember(item)
	}

	override fun chatMember(item: CMU) {
		for (i in list)
			i.chatMember(item)
	}

	override fun chatJoinRequest(item: CJR) {
		for (i in list)
			i.chatJoinRequest(item)
	}

	////////////

	override fun text(message: M) {
		for (i in list)
			i.text(message)
	}

	override fun newChatMember(message: M) {
		for (i in list)
			i.newChatMember(message)
	}

	override fun leftChatMember(message: M) {
		for (i in list)
			i.leftChatMember(message)
	}

	override fun newChatTitle(message: M) {
		for (i in list)
			i.newChatTitle(message)
	}

	override fun newChatPhoto(message: M) {
		for (i in list)
			i.newChatPhoto(message)
	}

	override fun deleteChatPhoto(message: M) {
		for (i in list)
			i.deleteChatPhoto(message)
	}

	override fun groupChatCreated(message: M) {
		for (i in list)
			i.groupChatCreated(message)
	}

	override fun supergroupChatCreated(message: M) {
		for (i in list)
			i.supergroupChatCreated(message)
	}

	override fun channelChatCreated(message: M) {
		for (i in list)
			i.channelChatCreated(message)
	}

	override fun messageAutoDeleteTimerChanged(message: M) {
		for (i in list)
			i.messageAutoDeleteTimerChanged(message)
	}

	override fun migrateToChatId(message: M) {
		for (i in list)
			i.migrateToChatId(message)
	}

	override fun migrateFromChatId(message: M) {
		for (i in list)
			i.migrateFromChatId(message)
	}

	override fun pinnedMessage(message: M) {
		for (i in list)
			i.pinnedMessage(message)
	}

	override fun invoice(message: M) {
		for (i in list)
			i.invoice(message)
	}

	override fun successfulPayment(message: M) {
		for (i in list)
			i.successfulPayment(message)
	}

	override fun connectedWebsite(message: M) {
		for (i in list)
			i.connectedWebsite(message)
	}

	override fun passportData(message: M) {
		for (i in list)
			i.passportData(message)
	}

	override fun proximityAlertTriggered(message: M) {
		for (i in list)
			i.proximityAlertTriggered(message)
	}

	override fun voiceChatScheduled(message: M) {
		for (i in list)
			i.voiceChatScheduled(message)
	}

	override fun voiceChatStarted(message: M) {
		for (i in list)
			i.voiceChatStarted(message)
	}

	override fun voiceChatEnded(message: M) {
		for (i in list)
			i.voiceChatEnded(message)
	}

	override fun voiceChatParticipantsInvited(message: M) {
		for (i in list)
			i.voiceChatParticipantsInvited(message)
	}

	override fun unresolvedMessage(message: M) {
		for (i in list)
			i.unresolvedMessage(message)
	}

	//////////////////

	override fun ownerChanged(item: CMU) {
		for (i in list)
			i.ownerChanged(item)
	}

	override fun administratorChanged(item: CMU) {
		for (i in list)
			i.administratorChanged(item)
	}

	override fun administratorCanceled(item: CMU) {
		for (i in list)
			i.administratorCanceled(item)
	}

	override fun chatMemberRestricted(item: CMU) {
		for (i in list)
			i.chatMemberRestricted(item)
	}

	override fun chatMemberLeft(item: CMU) {
		for (i in list)
			i.chatMemberLeft(item)
	}

	override fun chatMemberBanned(item: CMU) {
		for (i in list)
			i.chatMemberBanned(item)
	}

	override fun chatMemberNew(item: CMU) {
		for (i in list)
			i.chatMemberNew(item)
	}
}