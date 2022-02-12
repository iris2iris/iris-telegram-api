package iris.tg.trigger

import iris.tg.api.items.*

/**
 * @created 11.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TriggerHandlerPackBasicTypes : TriggerHandlerPack<Message
, InlineQuery
, ChosenInlineResult
, CallbackQuery
, ShippingQuery
, PreCheckoutQuery
, Poll
, PollAnswer
, ChatMemberUpdated
, ChatJoinRequest
>()