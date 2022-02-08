package iris.tg.processors.pack

import iris.tg.api.items.*

open class TgEventMessagePackFilterAdapter<M : Message
	, IQ : InlineQuery
	, CIR : ChosenInlineResult
	, CQ : CallbackQuery
	, SQ : ShippingQuery
	, PCQ: PreCheckoutQuery
	, P: Poll
	, PA: PollAnswer
	, CMU: ChatMemberUpdated
	, CJR: ChatJoinRequest
> : TgEventMessagePackFilter<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR> {

	override fun messages(messages: List<M>) = messages

	override fun editedMessages(messages: List<M>) = messages

	override fun channelPosts(messages: List<M>) = messages

	override fun editedChannelPosts(messages: List<M>) = messages

	override fun inlineQueries(items: List<IQ>) = items

	override fun chosenInlineResults(items: List<CIR>) = items

	override fun callbackQueries(items: List<CQ>) = items

	override fun shippingQueries(items: List<SQ>) = items

	override fun preCheckoutQueries(items: List<PCQ>) = items

	override fun polls(items: List<P>) = items

	override fun pollAnswers(items: List<PA>) = items

	override fun myChatMembers(items: List<CMU>) = items

	override fun chatMembers(items: List<CMU>) = items

	override fun chatJoinRequests(items: List<CJR>) = items



	override fun texts(messages: List<M>) = messages

	override fun newChatMembers(messages: List<M>) = messages

	override fun leftChatMember(messages: List<M>) = messages

	override fun newChatTitle(messages: List<M>) = messages

	override fun newChatPhoto(messages: List<M>) = messages

	override fun deleteChatPhoto(messages: List<M>) = messages

	override fun groupChatCreated(messages: List<M>) = messages

	override fun supergroupChatCreated(messages: List<M>) = messages

	override fun channelChatCreated(messages: List<M>) = messages

	override fun messageAutoDeleteTimerChanged(messages: List<M>) = messages

	override fun migrateToChatId(messages: List<M>) = messages

	override fun migrateFromChatId(messages: List<M>) = messages

	override fun pinnedMessage(messages: List<M>) = messages

	override fun invoice(messages: List<M>) = messages

	override fun successfulPayment(messages: List<M>) = messages

	override fun connectedWebsite(messages: List<M>) = messages

	override fun passportData(messages: List<M>) = messages

	override fun proximityAlertTriggered(messages: List<M>) = messages

	override fun voiceChatScheduled(messages: List<M>) = messages

	override fun voiceChatStarted(messages: List<M>) = messages

	override fun voiceChatEnded(messages: List<M>) = messages

	override fun voiceChatParticipantsInvited(messages: List<M>) = messages

	override fun unresolvedMessages(messages: List<M>): List<M> = messages

	///////////////////

	override fun ownersChanged(items: List<CMU>) = items

	override fun administratorsChanged(items: List<CMU>) = items

	override fun administratorsCanceled(items: List<CMU>) = items

	override fun chatMembersRestricted(items: List<CMU>) = items

	override fun chatMemberLeft(items: List<CMU>) = items

	override fun chatMemberBanned(items: List<CMU>) = items

	override fun chatMemberNew(items: List<CMU>) = items
}