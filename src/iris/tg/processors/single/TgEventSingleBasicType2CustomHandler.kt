package iris.tg.processors.single

import iris.tg.api.items.*

/**
 * @created 08.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TgEventSingleBasicType2CustomHandler<M : Message
		, IQ : InlineQuery
		, CIR : ChosenInlineResult
		, CQ : CallbackQuery
		, SQ : ShippingQuery
		, PCQ: PreCheckoutQuery
		, P: Poll
		, PA: PollAnswer
		, CMU: ChatMemberUpdated
		, CJR: ChatJoinRequest
>(private val handler: TgEventMessageSingleHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>) : TgEventMessageSingleHandlerBasicTypes {

	private fun <T>Any.cast() = this as T
	
	override fun ownerChanged(item: ChatMemberUpdated) {
		handler.ownerChanged(item.cast())
	}

	override fun administratorChanged(item: ChatMemberUpdated) {
		handler.administratorChanged(item.cast())
	}

	override fun administratorCanceled(item: ChatMemberUpdated) {
		handler.administratorCanceled(item.cast())
	}

	override fun chatMemberRestricted(item: ChatMemberUpdated) {
		handler.chatMemberRestricted(item.cast())
	}

	override fun chatMemberLeft(item: ChatMemberUpdated) {
		handler.chatMemberLeft(item.cast())
	}

	override fun chatMemberBanned(item: ChatMemberUpdated) {
		handler.chatMemberBanned(item.cast())
	}

	override fun chatMemberNew(item: ChatMemberUpdated) {
		handler.chatMemberNew(item.cast())
	}

	override fun message(message: Message) {
		handler.message(message.cast())
	}

	override fun editedMessage(message: Message) {
		handler.editedMessage(message.cast())
	}

	override fun channelPost(message: Message) {
		handler.channelPost(message.cast())
	}

	override fun editedChannelPost(message: Message) {
		handler.editedChannelPost(message.cast())
	}

	override fun inlineQuery(item: InlineQuery) {
		handler.inlineQuery(item.cast())
	}

	override fun chosenInlineResult(item: ChosenInlineResult) {
		handler.chosenInlineResult(item.cast())
	}

	override fun callbackQuery(item: CallbackQuery) {
		handler.callbackQuery(item.cast())
	}

	override fun shippingQuery(item: ShippingQuery) {
		handler.shippingQuery(item.cast())
	}

	override fun preCheckoutQuery(item: PreCheckoutQuery) {
		handler.preCheckoutQuery(item.cast())
	}

	override fun poll(item: Poll) {
		handler.poll(item.cast())
	}

	override fun pollAnswer(item: PollAnswer) {
		handler.pollAnswer(item.cast())
	}

	override fun myChatMember(item: ChatMemberUpdated) {
		handler.myChatMember(item.cast())
	}

	override fun chatMember(item: ChatMemberUpdated) {
		handler.chatMember(item.cast())
	}

	override fun chatJoinRequest(item: ChatJoinRequest) {
		handler.chatJoinRequest(item.cast())
	}

	override fun text(message: Message) {
		handler.text(message.cast())
	}

	override fun newChatMember(message: Message) {
		handler.newChatMember(message.cast())
	}

	override fun leftChatMember(message: Message) {
		handler.leftChatMember(message.cast())
	}

	override fun newChatTitle(message: Message) {
		handler.newChatTitle(message.cast())
	}

	override fun newChatPhoto(message: Message) {
		handler.newChatPhoto(message.cast())
	}

	override fun deleteChatPhoto(message: Message) {
		handler.deleteChatPhoto(message.cast())
	}

	override fun groupChatCreated(message: Message) {
		handler.groupChatCreated(message.cast())
	}

	override fun supergroupChatCreated(message: Message) {
		handler.supergroupChatCreated(message.cast())
	}

	override fun channelChatCreated(message: Message) {
		handler.channelChatCreated(message.cast())
	}

	override fun messageAutoDeleteTimerChanged(message: Message) {
		handler.messageAutoDeleteTimerChanged(message.cast())
	}

	override fun migrateToChatId(message: Message) {
		handler.migrateToChatId(message.cast())
	}

	override fun migrateFromChatId(message: Message) {
		handler.migrateFromChatId(message.cast())
	}

	override fun pinnedMessage(message: Message) {
		handler.pinnedMessage(message.cast())
	}

	override fun invoice(message: Message) {
		handler.invoice(message.cast())
	}

	override fun successfulPayment(message: Message) {
		handler.successfulPayment(message.cast())
	}

	override fun connectedWebsite(message: Message) {
		handler.connectedWebsite(message.cast())
	}

	override fun passportData(message: Message) {
		handler.passportData(message.cast())
	}

	override fun proximityAlertTriggered(message: Message) {
		handler.proximityAlertTriggered(message.cast())
	}

	override fun voiceChatScheduled(message: Message) {
		handler.voiceChatScheduled(message.cast())
	}

	override fun voiceChatStarted(message: Message) {
		handler.voiceChatStarted(message.cast())
	}

	override fun voiceChatEnded(message: Message) {
		handler.voiceChatEnded(message.cast())
	}

	override fun voiceChatParticipantsInvited(message: Message) {
		handler.voiceChatParticipantsInvited(message.cast())
	}

	override fun unresolvedMessage(message: Message) {
		handler.unresolvedMessage(message.cast())
	}
}