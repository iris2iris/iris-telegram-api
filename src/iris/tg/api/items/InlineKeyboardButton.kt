package iris.tg.api.items

interface InlineKeyboardButton {

	/** Label text on the button */
	val text: String

	/** Optional. HTTP or tg:// url to be opened when the button is pressed. Links tg://user?id=<user_id> can be used to mention a user by their ID without using a username, if this is allowed by their privacy settings. */
	val url: String?

	/** Optional. An HTTP URL used to automatically authorize the user. Can be used as a replacement for the Telegram Login Widget. */
	val login_url: LoginUrl?

	/** Optional. Data to be sent in a callback query to the botData when button is pressed, 1-64 bytes */
	val callback_data: String?

	/** Optional. If set, pressing the button will prompt the user to select one of their chats, open that chat and insert the botData's username and the specified inline query in the input field. Can be empty, in which case just the botData's username will be inserted.

	Note: This offers an easy way for users to start using your botData in inline mode when they are currently in a private chat with it. Especially useful when combined with switch_pm… actions – in this case the user will be automatically returned to the chat they switched from, skipping the chat selection screen.
	switch_inline_query_current_chat	String	Optional. If set, pressing the button will insert the botData's username and the specified inline query in the current chat's input field. Can be empty, in which case only the botData's username will be inserted.

	This offers a quick way for the user to open your botData in inline mode in the same chat – good for selecting something from multiple options. */
	val switch_inline_query: String?

	/** Optional. Description of the game that will be launched when the user presses the button.

	NOTE: This type of button must always be the first button in the first row.
	*/
	val callback_game: CallbackGame?


	/** Optional. Specify True, to send a Pay button.

	NOTE: This type of button must always be the first button in the first row and can only be used in invoice messages. */
	val pay: Boolean

}
