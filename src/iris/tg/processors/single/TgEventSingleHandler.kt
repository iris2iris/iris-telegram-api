package iris.tg.processors.single

import iris.tg.api.items.*
import iris.tg.processors.TgEventHandler

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgEventSingleHandler<M : Message
	, IQ : InlineQuery
	, CIR : ChosenInlineResult
	, CQ : CallbackQuery
	, SQ : ShippingQuery
	, PCQ: PreCheckoutQuery
	, P: Poll
	, PA: PollAnswer
	, CMU: ChatMemberUpdated
	, CJR: ChatJoinRequest
> : TgEventHandler {
	fun message(message: M)
	fun editedMessage(message: M)
	fun channelPost(message: M)
	fun editedChannelPost(message: M)
	fun inlineQuery(item: IQ)
	fun chosenInlineResult(item: CIR)
	fun callbackQuery(item: CQ)
	fun shippingQuery(item: SQ)
	fun preCheckoutQuery(item: PCQ)
	fun poll(item: P)
	fun pollAnswer(item: PA)
	fun myChatMember(item: CMU)
	fun chatMember(item: CMU)
	fun chatJoinRequest(item: CJR)
}

typealias TgEventSingleHandlerBasicTypes = TgEventSingleHandler<
	Message
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