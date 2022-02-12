package iris.tg.api.items

interface ChatPermissions {
	/** Optional. True, if the user is allowed to send text messages, contacts, locations and venues */
	val can_send_messages: Boolean?

	/** Optional. True, if the user is allowed to send audios, documents, photos, videos, video notes and voice notes, implies can_send_messages */
	val can_send_media_messages: Boolean?

	/** Optional. True, if the user is allowed to send polls, implies can_send_messages */
	val can_send_polls: Boolean?

	/** Optional. True, if the user is allowed to send animations, games, stickers and use inline bots, implies can_send_media_messages */
	val can_send_other_messages: Boolean?

	/** Optional. True, if the user is allowed to add web page previews to their messages, implies can_send_media_messages */
	val can_add_web_page_previews: Boolean?

	/** Optional. True, if the user is allowed to change the chat title, photo and other settings. Ignored in public supergroups */
	val can_change_info: Boolean?

	/** Optional. True, if the user is allowed to invite new users to the chat */
	val can_invite_users: Boolean?

	/** Optional. True, if the user is allowed to pin messages. Ignored in public supergroups */
	val can_pin_messages: Boolean?
}
