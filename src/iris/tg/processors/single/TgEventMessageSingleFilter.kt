package iris.tg.processors.single

import iris.tg.api.items.*

interface TgEventMessageSingleFilter<M : Message
		, IQ : InlineQuery
		, CIR : ChosenInlineResult
		, CQ : CallbackQuery
		, SQ : ShippingQuery
		, PCQ: PreCheckoutQuery
		, P: Poll
		, PA: PollAnswer
		, CMU: ChatMemberUpdated
		, CJR: ChatJoinRequest
> : TgEventSingleFilter<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>, TgMessageSingleFilter<M>, TgChatMemberUpdatedSingleFilter<CMU>