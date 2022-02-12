package iris.tg.py

import iris.tg.api.items.*
import iris.tg.py.items.PyCallbackQuery
import iris.tg.py.items.PyMessage
import iris.tg.trigger.TriggerHandlerSingle

/**
 * @created 11.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TriggerHandlerSinglePy : TriggerHandlerSingle<PyMessage
		, InlineQuery
		, ChosenInlineResult
		, PyCallbackQuery
		, ShippingQuery
		, PreCheckoutQuery
		, Poll
		, PollAnswer
		, ChatMemberUpdated
		, ChatJoinRequest
>() {
}