package iris.tg.processors.single

import iris.tg.api.items.*
import iris.tg.processors.TgEventHandler

/**
 * @created 20.09.2019
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgEventSingleFilter <M : Message
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
	fun message(message: M): M?
	fun editedMessage(message: M): M?
	fun channelPost(message: M): M?
	fun editedChannelPost(message: M): M?
	fun inlineQuery(item: IQ): IQ?
	fun chosenInlineResult(item: CIR): CIR?
	fun callbackQuery(item: CQ): CQ?
	fun shippingQuery(item: SQ): SQ?
	fun preCheckoutQuery(item: PCQ): PCQ?
	fun poll(item: P): P?
	fun pollAnswer(item: PA): PA?
	fun myChatMember(item: CMU): CMU?
	fun chatMember(item: CMU): CMU?
	fun chatJoinRequest(item: CJR): CJR?
}

typealias TgEventSingleFilterBasicTypes = TgEventSingleFilter<Message
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