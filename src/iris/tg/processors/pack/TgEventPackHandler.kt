package iris.tg.processors.pack

import iris.tg.api.items.*
import iris.tg.processors.TgEventHandler

interface TgEventPackHandler<M : Message
	, IQ : InlineQuery
	, CIR : ChosenInlineResult
	, CQ : CallbackQuery
	, SQ : ShippingQuery
	, PCO: PreCheckoutQuery
	, P: Poll
	, PA: PollAnswer
	, CMU: ChatMemberUpdated
	, CJR: ChatJoinRequest
>: TgEventHandler {
	fun messages(messages: List<M>)
	fun editedMessages(messages: List<M>)
	fun channelPosts(messages: List<M>)
	fun editedChannelPosts(messages: List<M>)
	fun inlineQueries(items: List<IQ>)
	fun chosenInlineResults(items: List<CIR>)
	fun callbackQueries(items: List<CQ>)
	fun shippingQueries(items: List<SQ>)
	fun preCheckoutQueries(items: List<PCO>)
	fun polls(items: List<P>)
	fun pollAnswers(items: List<PA>)
	fun myChatMembers(items: List<CMU>)
	fun chatMembers(items: List<CMU>)
	fun chatJoinRequests(items: List<CJR>)
}

typealias TgEventPackHandlerBasicTypes = TgEventPackHandler<Message
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