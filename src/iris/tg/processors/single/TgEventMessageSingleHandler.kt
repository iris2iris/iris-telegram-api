package iris.tg.processors.single

import iris.tg.api.items.*

interface TgEventMessageSingleHandler<M : Message
		, IQ : InlineQuery
		, CIR : ChosenInlineResult
		, CQ : CallbackQuery
		, SQ : ShippingQuery
		, PCQ: PreCheckoutQuery
		, P: Poll
		, PA: PollAnswer
		, CMU: ChatMemberUpdated
		, CJR: ChatJoinRequest
>: TgEventSingleHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>, TgMessageSingleHandler<M>, TgChatMemberUpdatedSingleHandler<CMU>

typealias TgEventMessageSingleHandlerBasicTypes = TgEventMessageSingleHandler<Message
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