package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.*

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonUpdate(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Update {

	override val updateId: Long
		get() = source["update_id"].asLong()

	override val message: Message? by lazyItemOrNull("message") { IrisJsonMessage(it) }
	override val editedMessage: Message? by lazyItemOrNull("edited_message") { IrisJsonMessage(it) }
	override val channelPost: Message? by lazyItemOrNull("channel_post") { IrisJsonMessage(it) }
	override val editedChannelPost: Message? by lazyItemOrNull("edited_channel_post") { IrisJsonMessage(it) }
	override val inlineQuery: InlineQuery? = null
	override val chosenInlineResult: ChosenInlineResult? = null
	override val callbackQuery: CallbackQuery? by lazyItemOrNull("callback_query") { IrisJsonCallbackQuery(it) }
	override val shippingQuery: ShippingQuery? = null
	override val preCheckoutQuery: PreCheckoutQuery? = null
	override val poll: Poll? = null
	override val pollAnswer: PollAnswer? = null
	override val myChatMember: ChatMemberUpdated? = null
	override val chatMember: ChatMemberUpdated? by lazyItemOrNull("chat_member") { IrisJsonChatMemberUpdated(it) }
	override val chatJoinRequest: ChatJoinRequest? = null
}