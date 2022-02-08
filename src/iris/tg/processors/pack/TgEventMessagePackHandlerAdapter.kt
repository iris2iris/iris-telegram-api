package iris.tg.processors.pack

import iris.tg.api.items.*

open class TgEventMessagePackHandlerAdapter<M : Message
		, IQ : InlineQuery
		, CIR : ChosenInlineResult
		, CQ : CallbackQuery
		, SQ : ShippingQuery
		, PCQ: PreCheckoutQuery
		, P: Poll
		, PA: PollAnswer
		, CMU: ChatMemberUpdated
		, CJR: ChatJoinRequest
> : TgEventMessagePackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR> {

	private val messageSplit by lazy { TgEventPackSplitMessageHandler(this) }
	private val memberUpdatedSplit by lazy { TgChatMemberUpdatedSplitter(this) }

	override fun messages(messages: List<M>) {
		messageSplit.messages(messages)
	}

	override fun editedMessages(messages: List<M>) {}

	override fun channelPosts(messages: List<M>) {}

	override fun editedChannelPosts(messages: List<M>) {}

	override fun inlineQueries(items: List<IQ>) {}

	override fun chosenInlineResults(items: List<CIR>) {}

	override fun callbackQueries(items: List<CQ>) {}

	override fun shippingQueries(items: List<SQ>) {}

	override fun preCheckoutQueries(items: List<PCQ>) {}

	override fun polls(items: List<P>) {}

	override fun pollAnswers(items: List<PA>) {}

	override fun myChatMembers(items: List<CMU>) {}

	override fun chatMembers(items: List<CMU>) {
		memberUpdatedSplit.process(items)
	}

	override fun chatJoinRequests(items: List<CJR>) {}

	//////////////////////

	override fun texts(messages: List<M>) {}

	override fun newChatMembers(messages: List<M>) {}

	override fun leftChatMember(messages: List<M>) {}

	override fun newChatTitle(messages: List<M>) {}

	override fun newChatPhoto(messages: List<M>) {}

	override fun deleteChatPhoto(messages: List<M>) {}

	override fun groupChatCreated(messages: List<M>) {}

	override fun supergroupChatCreated(messages: List<M>) {}

	override fun channelChatCreated(messages: List<M>) {}

	override fun messageAutoDeleteTimerChanged(messages: List<M>) {}

	override fun migrateToChatId(messages: List<M>) {}

	override fun migrateFromChatId(messages: List<M>) {}

	override fun pinnedMessage(messages: List<M>) {}

	override fun invoice(messages: List<M>) {}

	override fun successfulPayment(messages: List<M>) {}

	override fun connectedWebsite(messages: List<M>) {}

	override fun passportData(messages: List<M>) {}

	override fun proximityAlertTriggered(messages: List<M>) {}

	override fun voiceChatScheduled(messages: List<M>) {}

	override fun voiceChatStarted(messages: List<M>) {}

	override fun voiceChatEnded(messages: List<M>) {}

	override fun voiceChatParticipantsInvited(messages: List<M>) {}

	override fun unresolvedMessages(messages: List<M>) {}

	//////////////////////

	override fun ownersChanged(items: List<CMU>) {}

	override fun administratorsChanged(items: List<CMU>) {}

	override fun administratorsCanceled(items: List<CMU>) {}

	override fun chatMembersRestricted(items: List<CMU>) {}

	override fun chatMemberLeft(items: List<CMU>) {}

	override fun chatMemberBanned(items: List<CMU>) {}

	override fun chatMemberNew(items: List<CMU>) {

	}
}

typealias TgEventMessagePackHandlerAdapterBasicTypes = TgEventMessagePackHandlerAdapter<Message
	, InlineQuery
	, ChosenInlineResult
	, CallbackQuery
	, ShippingQuery
	, PreCheckoutQuery
	, Poll
	, PollAnswer
	, ChatMemberUpdated
	, ChatJoinRequest
>