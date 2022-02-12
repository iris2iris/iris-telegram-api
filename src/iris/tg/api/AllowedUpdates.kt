package iris.tg.api

/**
 * @created 12.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class AllowedUpdates(
	/** Message	Optional. New incoming message of any kind — text, photo, sticker, etc. */
	var message: Boolean = true,

	/** Message	Optional. New version of a message that is known to the bot and was edited */
	var edited_message: Boolean = true,

	/** Message	Optional. New incoming channel post of any kind — text, photo, sticker, etc. */
	var channel_post: Boolean = true,

	/** Message	Optional. New version of a channel post that is known to the bot and was edited */
	var edited_channel_post: Boolean = true,

	/** InlineQuery	Optional. New incoming inline query */
	var inline_query: Boolean = true,

	/** ChosenInlineResult	Optional. The result of an inline query that was chosen by a user and sent to their chat partner. Please see our documentation on the feedback collecting for details on how to enable these updates for your bot. */
	var chosen_inline_result: Boolean = true,

	/** CallbackQuery	Optional. New incoming callback query */
	var callback_query: Boolean = true,

	/** ShippingQuery	Optional. New incoming shipping query. Only for invoices with flexible price */
	var shipping_query: Boolean = true,

	/** PreCheckoutQuery	Optional. New incoming pre-checkout query. Contains full information about checkout */
	var pre_checkout_query: Boolean = true,

	/** Poll	Optional. New poll state. Bots receive only updates about stopped polls and polls, which are sent by the bot */
	var poll: Boolean = true,

	/** PollAnswer	Optional. A user changed their answer in a non-anonymous poll. Bots receive new votes only in polls that were sent by the bot itself. */
	var poll_answer: Boolean = true,

	/** ChatMemberUpdated	Optional. The bot's chat member status was updated in a chat. For private chats, this update is received only when the bot is blocked or unblocked by the user. */
	var my_chat_member: Boolean = true,

	/** ChatMemberUpdated	Optional. A chat member's status was updated in a chat. The bot must be an administrator in the chat and must explicitly specify “chat_member” in the list of allowed_updates to receive these updates. */
	var chat_member: Boolean = false,

	/** ChatJoinRequest	Optional. A request to join the chat has been sent. The bot must have the can_invite_users administrator right in the chat to receive these updates. */
	var chat_join_request: Boolean = true,


) {

	fun toArray(): List<String> {
		val arr = ArrayList<String>(14)
		if (message) arr += "message"
		if (edited_message) arr += "edited_message"
		if (channel_post) arr += "channel_post"
		if (edited_channel_post) arr += "edited_channel_post"
		if (inline_query) arr += "inline_query"
		if (chosen_inline_result) arr += "chosen_inline_result"
		if (callback_query) arr += "callback_query"
		if (shipping_query) arr += "shipping_query"
		if (pre_checkout_query) arr += "pre_checkout_query"
		if (poll) arr += "poll"
		if (poll_answer) arr += "poll_answer"
		if (my_chat_member) arr += "my_chat_member"
		if (chat_member) arr += "chat_member"
		if (chat_join_request) arr += "chat_join_request"
		return arr
	}

	companion object {
		val All get() = AllowedUpdates().also { it.chat_member = true }
	}

}