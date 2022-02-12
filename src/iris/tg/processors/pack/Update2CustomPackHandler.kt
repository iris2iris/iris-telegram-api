package iris.tg.processors.pack

import iris.tg.api.items.*

/**
 * @created 11.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class Update2CustomPackHandler<M : Message
	, IQ : InlineQuery
	, CIR : ChosenInlineResult
	, CQ : CallbackQuery
	, SQ : ShippingQuery
	, PCQ: PreCheckoutQuery
	, P: Poll
	, PA: PollAnswer
	, CMU: ChatMemberUpdated
	, CJR: ChatJoinRequest
>(private val handler: TgEventPackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>) : TgEventPackHandlerBasicTypes {

	private fun <T>Any.cast() = this as T

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
}