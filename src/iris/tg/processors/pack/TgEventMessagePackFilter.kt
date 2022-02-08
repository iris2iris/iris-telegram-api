package iris.tg.processors.pack

import iris.tg.api.items.*

interface TgEventMessagePackFilter<M : Message
	, IQ : InlineQuery
	, CIR : ChosenInlineResult
	, CQ : CallbackQuery
	, SQ : ShippingQuery
	, PCQ: PreCheckoutQuery
	, P: Poll
	, PA: PollAnswer
	, CMU: ChatMemberUpdated
	, CJR: ChatJoinRequest
> : TgEventPackFilter<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>, TgMessagePackFilter<M>, TgChatMemberUpdatedFilter<CMU>