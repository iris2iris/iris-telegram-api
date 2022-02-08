package iris.tg.processors.pack

import iris.tg.api.items.*

interface TgEventMessagePackHandler<M : Message
	, IQ : InlineQuery
	, CIR : ChosenInlineResult
	, CQ : CallbackQuery
	, SQ : ShippingQuery
	, PCQ: PreCheckoutQuery
	, P: Poll
	, PA: PollAnswer
	, CMU: ChatMemberUpdated
	, CJR: ChatJoinRequest
>: TgEventPackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>, TgMessagePackHandler<M>, TgChatMemberUpdatedHandler<CMU> {
}

typealias TgEventMessagePackHandlerBasicTypes = TgEventMessagePackHandler<Message
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