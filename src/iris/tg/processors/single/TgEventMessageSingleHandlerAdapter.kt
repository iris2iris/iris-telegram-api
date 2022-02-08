package iris.tg.processors.single

import iris.tg.api.items.*

open class TgEventMessageSingleHandlerAdapter<M : Message
		, IQ : InlineQuery
		, CIR : ChosenInlineResult
		, CQ : CallbackQuery
		, SQ : ShippingQuery
		, PCQ: PreCheckoutQuery
		, P: Poll
		, PA: PollAnswer
		, CMU: ChatMemberUpdated
		, CJR: ChatJoinRequest
> : TgEventMessageSingleHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR> {

	private val messageSplit by lazy { TgEventSplitMessageHandler(this) }
	
	private val memberUpdatedSplit by lazy { TgChatMemberUpdatedSplitter(this) }

	override fun message(message: M) {
		messageSplit.message(message)
	}

	override fun chatMember(item: CMU) {
		memberUpdatedSplit.process(item)
	}

	override fun ownerChanged(item: CMU) {}

	override fun administratorChanged(item: CMU) {}

	override fun administratorCanceled(item: CMU) {}

	override fun chatMemberRestricted(item: CMU) {}

	override fun chatMemberLeft(item: CMU) {}

	override fun chatMemberBanned(item: CMU) {}

	override fun chatMemberNew(item: CMU) {}

	override fun editedMessage(message: M) {}

	override fun channelPost(message: M) {}

	override fun editedChannelPost(message: M) {}

	override fun inlineQuery(item: IQ) {}

	override fun chosenInlineResult(item: CIR) {}

	override fun callbackQuery(item: CQ) {}

	override fun shippingQuery(item: SQ) {}

	override fun preCheckoutQuery(item: PCQ) {}

	override fun poll(item: P) {}

	override fun pollAnswer(item: PA) {}

	override fun myChatMember(item: CMU) {}

	override fun chatJoinRequest(item: CJR) {}

	override fun text(message: M) {}

	override fun newChatMember(message: M) {}

	override fun leftChatMember(message: M) {}

	override fun newChatTitle(message: M) {}

	override fun newChatPhoto(message: M) {}

	override fun deleteChatPhoto(message: M) {}

	override fun groupChatCreated(message: M) {}

	override fun supergroupChatCreated(message: M) {}

	override fun channelChatCreated(message: M) {}

	override fun messageAutoDeleteTimerChanged(message: M) {}

	override fun migrateToChatId(message: M) {}

	override fun migrateFromChatId(message: M) {}

	override fun pinnedMessage(message: M) {}

	override fun invoice(message: M) {}

	override fun successfulPayment(message: M) {}

	override fun connectedWebsite(message: M) {}

	override fun passportData(message: M) {}

	override fun proximityAlertTriggered(message: M) {}

	override fun voiceChatScheduled(message: M) {}

	override fun voiceChatStarted(message: M) {}

	override fun voiceChatEnded(message: M) {}

	override fun voiceChatParticipantsInvited(message: M) {}

	override fun unresolvedMessage(message: M) {}
}

open class TgEventMessageSingleHandlerAdapterBasicTypes : TgEventMessageSingleHandlerAdapter<Message
, InlineQuery
, ChosenInlineResult
, CallbackQuery
, ShippingQuery
, PreCheckoutQuery
, Poll
, PollAnswer
, ChatMemberUpdated
, ChatJoinRequest
>()