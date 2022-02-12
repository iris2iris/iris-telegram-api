package iris.tg.processors.single

import iris.tg.api.items.*

/**
 * @created 11.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class Update2CustomSingleHandler<M : Message
	, IQ : InlineQuery
	, CIR : ChosenInlineResult
	, CQ : CallbackQuery
	, SQ : ShippingQuery
	, PCQ: PreCheckoutQuery
	, P: Poll
	, PA: PollAnswer
	, CMU: ChatMemberUpdated
	, CJR: ChatJoinRequest
>(private val handler: TgEventSingleHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>) : TgEventSingleHandlerBasicTypes {

	private fun <T>Any.cast() = this as T

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
}