package iris.tg.processors.single

import iris.tg.api.items.*

open class TgEventMessageSingleFilterAdapter<M : Message
	, IQ : InlineQuery
	, CIR : ChosenInlineResult
	, CQ : CallbackQuery
	, SQ : ShippingQuery
	, PCQ: PreCheckoutQuery
	, P: Poll
	, PA: PollAnswer
	, CMU: ChatMemberUpdated
	, CJR: ChatJoinRequest
> : TgEventMessageSingleFilter<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR> {

	override fun message(message: M): M? = message

	override fun editedMessage(message: M): M? = message

	override fun channelPost(message: M): M? = message

	override fun editedChannelPost(message: M): M? = message

	override fun inlineQuery(item: IQ): IQ? = item

	override fun chosenInlineResult(item: CIR): CIR? = item

	override fun callbackQuery(item: CQ): CQ? = item

	override fun shippingQuery(item: SQ): SQ? = item

	override fun preCheckoutQuery(item: PCQ): PCQ? = item

	override fun poll(item: P): P? = item

	override fun pollAnswer(item: PA): PA? = item

	override fun myChatMember(item: CMU): CMU? = item

	override fun chatMember(item: CMU): CMU? = item

	override fun chatJoinRequest(item: CJR): CJR? = item

	///////////////////
	override fun text(message: M): M? = message

	override fun newChatMember(message: M): M? = message

	override fun leftChatMember(message: M): M? = message

	override fun newChatTitle(message: M): M? = message

	override fun newChatPhoto(message: M): M? = message

	override fun deleteChatPhoto(message: M): M? = message

	override fun groupChatCreated(message: M): M? = message

	override fun supergroupChatCreated(message: M): M? = message

	override fun channelChatCreated(message: M): M? = message

	override fun messageAutoDeleteTimerChanged(message: M): M? = message

	override fun migrateToChatId(message: M): M? = message

	override fun migrateFromChatId(message: M): M? = message

	override fun pinnedMessage(message: M): M? = message

	override fun invoice(message: M): M? = message

	override fun successfulPayment(message: M): M? = message

	override fun connectedWebsite(message: M): M? = message

	override fun passportData(message: M): M? = message

	override fun proximityAlertTriggered(message: M): M? = message

	override fun voiceChatScheduled(message: M): M? = message

	override fun voiceChatStarted(message: M): M? = message

	override fun voiceChatEnded(message: M): M? = message

	override fun voiceChatParticipantsInvited(message: M): M? = message

	override fun unresolvedMessage(message: M): M? = message

	///////////////////

	override fun ownerChanged(item: CMU): CMU = item

	override fun administratorChanged(item: CMU): CMU = item

	override fun administratorCanceled(item: CMU): CMU = item

	override fun chatMemberRestricted(item: CMU): CMU = item

	override fun chatMemberLeft(item: CMU): CMU = item

	override fun chatMemberBanned(item: CMU): CMU = item

	override fun chatMemberNew(item: CMU): CMU = item
}