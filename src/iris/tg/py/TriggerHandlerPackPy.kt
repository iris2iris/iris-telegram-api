package iris.tg.py

import iris.tg.api.items.*
import iris.tg.py.items.PyCallbackQuery
import iris.tg.py.items.PyMessage
import iris.tg.trigger.TriggerHandlerPack

/**
 * @created 12.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TriggerHandlerPackPy : TriggerHandlerPack<PyMessage
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