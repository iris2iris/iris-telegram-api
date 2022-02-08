package iris.tg.processors.pack

import iris.tg.api.items.*

/**
 * @created 20.09.2019
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgEventPackFilter<M : Message
	, IQ : InlineQuery
	, CIR : ChosenInlineResult
	, CQ : CallbackQuery
	, SQ : ShippingQuery
	, PCQ: PreCheckoutQuery
	, P: Poll
	, PA: PollAnswer
	, CMU: ChatMemberUpdated
	, CJR: ChatJoinRequest
> {
	fun messages(messages: List<M>) : List<M>
	fun editedMessages(messages: List<M>) : List<M>
	fun channelPosts(messages: List<M>) : List<M>
	fun editedChannelPosts(messages: List<M>) : List<M>
	fun inlineQueries(items: List<IQ>): List<IQ>
	fun chosenInlineResults(items: List<CIR>): List<CIR>
	fun callbackQueries(items: List<CQ>): List<CQ>
	fun shippingQueries(items: List<SQ>): List<SQ>
	fun preCheckoutQueries(items: List<PCQ>): List<PCQ>
	fun polls(items: List<P>): List<P>
	fun pollAnswers(items: List<PA>): List<PA>
	fun myChatMembers(items: List<CMU>): List<CMU>
	fun chatMembers(items: List<CMU>): List<CMU>
	fun chatJoinRequests(items: List<CJR>): List<CJR>
}

