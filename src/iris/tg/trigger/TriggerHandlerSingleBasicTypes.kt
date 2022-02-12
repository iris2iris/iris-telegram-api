package iris.tg.trigger

import iris.tg.api.items.*

/**
 * @created 11.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TriggerHandlerSingleBasicTypes() : TriggerHandlerSingle<Message
, InlineQuery
, ChosenInlineResult
, CallbackQuery
, ShippingQuery
, PreCheckoutQuery
, Poll
, PollAnswer
, ChatMemberUpdated
, ChatJoinRequest
>() {
	constructor(initializer: TriggerHandlerSingleBasicTypes.() -> Unit) : this() {
		apply(initializer)
	}
}