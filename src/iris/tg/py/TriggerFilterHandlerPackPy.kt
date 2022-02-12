package iris.tg.py

import iris.tg.api.items.*
import iris.tg.py.items.PyCallbackQuery
import iris.tg.py.items.PyMessage
import iris.tg.trigger.TriggerHandlerPack
import iris.tg.trigger.TriggerPackFilterHandler

/**
 * @created 12.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TriggerFilterHandlerPackPy(handler: TriggerHandlerPackPy) : TriggerPackFilterHandler<PyMessage
	, InlineQuery
	, ChosenInlineResult
	, PyCallbackQuery
	, ShippingQuery
	, PreCheckoutQuery
	, Poll
	, PollAnswer
	, ChatMemberUpdated
	, ChatJoinRequest
>(handler) {
}