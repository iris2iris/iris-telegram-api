package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Update : TgItem {
	val updateId: Long
	val message: Message?
	val editedMessage: Message?
	val channelPost: Message?
	val editedChannelPost: Message?
	val inlineQuery: InlineQuery?
	val chosenInlineResult: ChosenInlineResult?
	val callbackQuery: CallbackQuery?
	val shippingQuery: ShippingQuery?
	val preCheckoutQuery: PreCheckoutQuery?
	val poll: Poll?
	val pollAnswer: PollAnswer?
	val myChatMember: ChatMemberUpdated?
	val chatMember: ChatMemberUpdated?
	val chatJoinRequest: ChatJoinRequest?
}