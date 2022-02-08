package iris.tg.py

import iris.tg.api.items.*
import iris.tg.processors.pack.TgEventMessagePackFilter
import iris.tg.processors.pack.TgEventMessagePackFilterAdapter
import iris.tg.processors.pack.TgEventMessagePackHandler
import iris.tg.processors.pack.TgEventMessagePackHandlerAdapter
import iris.tg.processors.single.*
import iris.tg.py.items.PyCallbackQuery
import iris.tg.py.items.PyMessage

interface PySingleHandler: TgEventMessageSingleHandler<PyMessage, InlineQuery, ChosenInlineResult, PyCallbackQuery, ShippingQuery, PreCheckoutQuery, Poll, PollAnswer, ChatMemberUpdated, ChatJoinRequest>
interface PyPackHandler: TgEventMessagePackHandler<PyMessage, InlineQuery, ChosenInlineResult, PyCallbackQuery, ShippingQuery, PreCheckoutQuery, Poll, PollAnswer, ChatMemberUpdated, ChatJoinRequest>

open class PySingleHandlerAdapter: TgEventMessageSingleHandlerAdapter<PyMessage, InlineQuery, ChosenInlineResult, PyCallbackQuery, ShippingQuery, PreCheckoutQuery, Poll, PollAnswer, ChatMemberUpdated, ChatJoinRequest>(), PySingleHandler
open class PyPackHandlerAdapter: TgEventMessagePackHandlerAdapter<PyMessage, InlineQuery, ChosenInlineResult, PyCallbackQuery, ShippingQuery, PreCheckoutQuery, Poll, PollAnswer, ChatMemberUpdated, ChatJoinRequest>(), PyPackHandler

interface PySingleFilter: TgEventMessageSingleFilter<PyMessage, InlineQuery, ChosenInlineResult, PyCallbackQuery, ShippingQuery, PreCheckoutQuery, Poll, PollAnswer, ChatMemberUpdated, ChatJoinRequest>
interface PyPackFilter: TgEventMessagePackFilter<PyMessage, InlineQuery, ChosenInlineResult, PyCallbackQuery, ShippingQuery, PreCheckoutQuery, Poll, PollAnswer, ChatMemberUpdated, ChatJoinRequest>

open class PySingleFilterAdapter: TgEventMessageSingleFilterAdapter<PyMessage, InlineQuery, ChosenInlineResult, PyCallbackQuery, ShippingQuery, PreCheckoutQuery, Poll, PollAnswer, ChatMemberUpdated, ChatJoinRequest>(), PySingleFilter
open class PyPackFilterAdapter: TgEventMessagePackFilterAdapter<PyMessage, InlineQuery, ChosenInlineResult, PyCallbackQuery, ShippingQuery, PreCheckoutQuery, Poll, PollAnswer, ChatMemberUpdated, ChatJoinRequest>(), PyPackFilter

class PySingleHandlerConverter(handler: PySingleHandler)
	: TgEventSingleBasicType2CustomHandler<PyMessage, InlineQuery, ChosenInlineResult, PyCallbackQuery, ShippingQuery, PreCheckoutQuery, Poll, PollAnswer, ChatMemberUpdated, ChatJoinRequest>
			(handler)

class PyPackHandlerConverter(handler: PySingleHandler)
	: TgEventSingleBasicType2CustomHandler<PyMessage, InlineQuery, ChosenInlineResult, PyCallbackQuery, ShippingQuery, PreCheckoutQuery, Poll, PollAnswer, ChatMemberUpdated, ChatJoinRequest>
	(handler)