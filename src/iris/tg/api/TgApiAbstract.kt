@file:Suppress("MemberVisibilityCanBePrivate")

package iris.tg.api

import iris.tg.api.items.*
import iris.tg.connection.Connection.*
import iris.tg.connection.query.MutableQuery
import iris.tg.connection.query.Query
import iris.tg.connection.query.mutableQuery
import iris.tg.connection.query.query
import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/*typealias Options = Map<String, Any?>
typealias MutableOptions = MutableMap<String, Any?>
typealias HashOptions = HashMap<String, Any?>*/


abstract class TgApiAbstract<ResponseType>(protected val token: String, apiPath: String? = null, val defaultSendSettings: SendDefaults = SendDefaults()): Jsonifier {

	private val apiPath = apiPath ?: "https://api.telegram.org"

	private val urlCache = HashMap<String, String>()

	var jsonifier: Jsonifier = JsonifierSimple()

	override fun entities(entities: List<MessageEntity>) = jsonifier.entities(entities)

	override fun replyMarkup(replyMarkup: ReplyMarkup) = jsonifier.replyMarkup(replyMarkup)

	override fun chatPermissions(permissions: ChatPermissions): String = jsonifier.chatPermissions(permissions)

	open fun json(permissions: ChatPermissions) = jsonifier.chatPermissions(permissions)

	open fun json(replyMarkup: ReplyMarkup) = jsonifier.replyMarkup(replyMarkup)

	open fun jsonEntities(entities: List<MessageEntity>) = jsonifier.entities(entities)

	open fun jsonMediaList(items: List<InputMedia>) = jsonifier.inputMedia(items)

	open fun json(array: Array<String>): String = jsonifier.array2JsonString(array)

	override fun array2JsonString(array: Array<String>): String = jsonifier.array2JsonString(array)
	override fun array2JsonString(array: List<String>): String = jsonifier.array2JsonString(array)

	override fun inputMedia(inputMedia: List<InputMedia>): String = jsonifier.inputMedia(inputMedia)

	fun request(method: String, options: Query?): ResponseType {
		val url = urlCache.getOrPut(method) { "$apiPath/bot${this.token}/$method" }
		return requestImpl(url, method, options)
	}

	protected abstract fun requestImpl(url: String, method: String, options: Query?): ResponseType
	protected abstract fun requestUploadImpl(url: String, method: String, files: Map<String, BinaryData>, options: Query?): ResponseType

	fun requestUpload(method: String, options: Query?, files: Map<String, BinaryData>): ResponseType {
		val url = urlCache.getOrPut(method) { "$apiPath/bot${this.token}/$method?" }
		return requestUploadImpl(url, method, files, options)
	}

	private fun encode(o: String): String? {
		return URLEncoder.encode(o, StandardCharsets.UTF_8)
	}

	/**
	 * Use this method to send text messages. On success, the sent Message is returned.

	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [text]	String	Yes	Text of the message to be sent, 1-4096 characters after entities parsing
	 * * [parse_mode]	String	Optional	Mode for parsing entities in the message text. See formatting options for more details.
	 * * [entities]	Array of MessageEntity	Optional	A JSON-serialized list of special entities that appear in message text, which can be specified instead of parse_mode
	 * * [disable_web_page_preview]	Boolean	Optional	Disables link previews for links in this message
	 * * [disable_notification]	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * * [protect_content]	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * * [reply_to_message_id]	Integer	Optional	If the message is a reply, ID of the original message
	 * * [allow_sending_without_reply]	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * * [reply_markup]	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	open fun sendMessage(chat_id: String, text: String, parse_mode: String? = null, entities: List<MessageEntity>? = null, disable_web_page_preview: Boolean? = null,
		disable_notification: Boolean? = null, protect_content: Boolean? = null, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null,
		reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id, "text" to text)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("entities", entities?.let(jsonifier::entities))
		options.putIfNotEmpty("disable_web_page_preview", disable_web_page_preview ?: defaultSendSettings.disableWebPagePreview)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		return request("sendMessage", options)
	}

	open fun sendMessage(chat_id: Long, text: String, parse_mode: String? = null, entities: List<MessageEntity>? = null, disable_web_page_preview: Boolean? = null,
						 disable_notification: Boolean? = null, protect_content: Boolean? = null, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null,
						 reply_markup: String? = null
	): ResponseType = sendMessage(chat_id.toString(), text, parse_mode, entities, disable_web_page_preview, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	/**
	 * Use this method to edit text and game messages. On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.

	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Optional	Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [message_id]	Integer	Optional	Required if inline_message_id is not specified. Identifier of the message to edit
	 * * [inline_message_id]	String	Optional	Required if chat_id and message_id are not specified. Identifier of the inline message
	 * * [text]	String	Yes	New text of the message, 1-4096 characters after entities parsing
	 * * [parse_mode]	String	Optional	Mode for parsing entities in the message text. See formatting options for more details.
	 * * [entities]	Array of MessageEntity	Optional	A JSON-serialized list of special entities that appear in message text, which can be specified instead of parse_mode
	 * * [disable_web_page_preview]	Boolean	Optional	Disables link previews for links in this message
	 * * [reply_markup]	InlineKeyboardMarkup	Optional	A JSON-serialized object for an inline keyboard.
	 */
	open fun editMessageText(text: String, chat_id: String? = null, message_id: Int = 0, inline_message_id: String? = null,
		parse_mode: String? = null, entities: String? = null, disable_web_page_preview: Boolean? = null, reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery()
		options.putIfNotEmpty("chat_id", chat_id)
		options.putIfNotEmpty("message_id", message_id)
		options.putIfNotEmpty("inline_message_id", inline_message_id)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("entities", entities)
		options.putIfNotEmpty("disable_web_page_preview", disable_web_page_preview ?: defaultSendSettings.disableWebPagePreview)
		options.putIfNotEmpty("reply_markup", reply_markup)

		options["text"] = text
		return request("editMessageText", options)
	}

	open fun editMessageText(chat_id: String, message_id: Int, text: String
		, parse_mode: String? = null, entities: String? = null, disable_web_page_preview: Boolean? = null, reply_markup: String? = null
	): ResponseType = editMessageText(text, chat_id, message_id, null, parse_mode, entities, disable_web_page_preview, reply_markup)

	open fun editMessageText(chat_id: Long, message_id: Int, text: String
		, parse_mode: String? = null, entities: String? = null, disable_web_page_preview: Boolean? = null, reply_markup: String? = null
	): ResponseType = editMessageText(text, chat_id.toString(), message_id, null, parse_mode, entities, disable_web_page_preview, reply_markup)

	open fun editMessageText(inline_message_id: String, text: String
		, parse_mode: String? = null, entities: String? = null, disable_web_page_preview: Boolean? = null, reply_markup: String? = null
	): ResponseType = editMessageText(text, null, 0, inline_message_id, parse_mode, entities, disable_web_page_preview, reply_markup)

	fun chatId(chat_id: Long): String = chat_id.toString()
	fun chatId(chat_id: String): String = chat_id

	fun binary(bytes: ByteArray) : BinaryData
		= BinaryDataByteArray(bytes)

	fun binary(file: String) : BinaryData
		= binary(File(file))

	fun binary(file: File) : BinaryData
		= BinaryDataFile(file)

	open fun getUpdates(offset: Long, limit: Int = 0, timeout: Int = 10, allowedUpdates: AllowedUpdates? = null): ResponseType {
		val options = mutableQuery(4)
		options.putIfNotEmpty("offset", offset)
		options.putIfNotEmpty("limit", limit)
		options.putIfNotEmpty("timeout", timeout)
		options.putIfNotEmpty("allowed_updates", allowedUpdates?.let { array2JsonString(it.toArray()) })
		return request("getUpdates", options)
	}

	open fun getChatMember(chatId: String, userId: Long): ResponseType {
		return request("getChatMember", query("chat_id" to chatId, "user_id" to userId))
	}

	open fun getChatMember(chatId: Long, userId: Long): ResponseType = getChatMember(chatId.toString(), userId)

	open fun getChatAdministrators(chatId: String): ResponseType {
		return request("getChatAdministrators", query("chat_id", chatId))
	}

	open fun getChatAdministrators(chatId: Long): ResponseType = getChatAdministrators(chatId.toString())

	open fun getChatMemberCount(chatId: String): ResponseType {
		return request("getChatMemberCount", query("chat_id", chatId))
	}

	open fun getChatMemberCount(chatId: Long): ResponseType = getChatMemberCount(chatId.toString())

	open fun banChatMember(chatId: String, userId: Long, untilDate: Int = 0): ResponseType {
		val options = mutableQuery(3, "chat_id" to chatId, "user_id" to userId)
		if (untilDate != 0)
			options["until_date"] = untilDate
		return request("banChatMember", options)
	}

	open fun banChatMember(chatId: Long, userId: Long, untilDate: Int = 0): ResponseType = banChatMember(chatId.toString(), userId, untilDate)

	open fun unbanChatMember(chatId: String, userId: Long): ResponseType {
		return request("unbanChatMember", query("chat_id" to chatId, "user_id" to userId))
	}

	open fun unbanChatMember(chatId: Long, userId: Long): ResponseType  = unbanChatMember(chatId.toString(), userId)

	open fun getChat(chatId: String): ResponseType {
		return request("getChat", query("chat_id", chatId))
	}

	open fun getChat(chatId: Long): ResponseType = getChat(chatId.toString())

	open fun deleteMessage(chatId: String, localId: Int): ResponseType {
		return request("deleteMessage", query("chat_id" to chatId, "message_id" to localId))
	}

	open fun deleteMessage(chatId: Long, localId: Int): ResponseType = deleteMessage(chatId.toString(), localId)

	/**
	 * Use this method to send photos. On success, the sent Message is returned.

	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [photo]	InputFile or String	Yes	Photo to send. Pass a file_id as String to send a photo that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a photo from the Internet, or upload a new photo using multipart/form-data. The photo must be at most 10 MB in size. The photo's width and height must not exceed 10000 in total. Width and height ratio must be at most 20. More info on Sending Files »
	 * * [caption]	String	Optional	Photo caption (may also be used when resending photos by file_id), 0-1024 characters after entities parsing
	 * * [parse_mode]	String	Optional	Mode for parsing entities in the photo caption. See formatting options for more details.
	 * * [caption_entities]	Array of MessageEntity	Optional	A JSON-serialized list of special entities that appear in the caption, which can be specified instead of parse_mode
	 * * [disable_notification]	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * * [protect_content]	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * * [reply_to_message_id]	Integer	Optional	If the message is a reply, ID of the original message
	 * * [allow_sending_without_reply]	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * * [reply_markup]	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	open fun sendPhoto(chat_id: String, file: BinaryData, caption: String? = null, parse_mode: String? = null, caption_entities: List<MessageEntity>? = null
		, disable_notification: Boolean? = null, protect_content: Boolean? = null, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		return requestUpload("sendPhoto", options, mapOf("photo" to file))
	}

	open fun sendPhoto(chat_id: Long, file: BinaryData, caption: String? = null, parse_mode: String? = null, caption_entities: List<MessageEntity>? = null
	   , disable_notification: Boolean? = null, protect_content: Boolean? = null, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null
	   , reply_markup: String? = null
	): ResponseType = sendPhoto(chat_id.toString(), file, caption, parse_mode, caption_entities, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	open fun sendPhoto(chat_id: String, fileId: String, caption: String? = null, parse_mode: String? = null, caption_entities: List<MessageEntity>? = null
	   , disable_notification: Boolean? = null, protect_content: Boolean? = null, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null
	   , reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		return request("sendPhoto", options)
	}

	open fun sendPhoto(chat_id: Long, fileId: String, caption: String? = null, parse_mode: String? = null, caption_entities: List<MessageEntity>? = null
					   , disable_notification: Boolean? = null, protect_content: Boolean? = null, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null
					   , reply_markup: String? = null
	): ResponseType = sendPhoto(chat_id.toString(), fileId, caption, parse_mode, caption_entities, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	open fun getFile(fileId: String): ResponseType {
		return request("getFile", query("file_id", fileId))
	}

	open fun getPath(filePath: String): String {
		return "$apiPath/file/bot$token/$filePath"
	}

	open fun answerCallbackQuery(callbackQueryId: String, text: String? = null, showAlert: Boolean = false, url: String? = null, cacheTime: Int = 0): ResponseType {
		val options = mutableQuery(5, "callback_query_id" to callbackQueryId)
		if (text != null) options["text"] = text
		if (showAlert) options["show_alert"] = showAlert
		if (url != null) options["url"] = url
		if (cacheTime != 0) options["cache_time"] = cacheTime
		return request("answerCallbackQuery", options)
	}

	/**
	 * Use this method to restrict a user in a supergroup. The bot must be an administrator in the supergroup for this to work and must have the appropriate administrator rights. Pass True for all permissions to lift restrictions from a user. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
	 * * [user_id]	Integer	Yes	Unique identifier of the target user
	 * * [permissions]	ChatPermissions	Yes	A JSON-serialized object for new user permissions
	 * * [until_date]	Integer	Optional	Date when restrictions will be lifted for the user, unix time. If user is restricted for more than 366 days or less than 30 seconds from the current time, they are considered to be restricted forever
	 */
	open fun restrictChatMember(chatId: String, userId: Long, permissions: String, untilDate: Int = 0): ResponseType {
		val options = mutableQuery(4, "chat_id" to chatId, "user_id" to userId, "permissions" to permissions)
		options.putIfNotEmpty("until_date", untilDate)
		return request("restrictChatMember", options)
	}

	open fun restrictChatMember(chatId: Long, userId: Long, permissions: String, untilDate: Int): ResponseType
		= restrictChatMember(chatId.toString(), userId, permissions, untilDate)

	open fun setWebhook(url: String, certificate: BinaryData? = null, ipAddress: String? = null, maxConnections: Int = 100, allowedUpdates: Array<String>? = null, dropPendingUpdates: Boolean = false): ResponseType {
		val options = mutableQuery(5, "url" to url, "max_connections" to maxConnections)

		options.putIfNotEmpty("ip_address", ipAddress)
		options.putIfNotEmpty("drop_pending_updates", dropPendingUpdates)

		allowedUpdates?.also {
			options["allowed_updates"] = array2JsonString(it)
		}

		return if (certificate == null)
			request("setWebhook", options)
		else
			requestUpload("setWebhook", options, mapOf("certificate" to certificate))
	}

	open fun deleteWebhook(dropPendingUpdates: Boolean = false): ResponseType {
		return request("deleteWebhook", if (dropPendingUpdates) query("drop_pending_updates", dropPendingUpdates) else null)
	}

	open fun getWebhookInfo(): ResponseType {
		return request("getWebhookInfo", null)
	}


	fun getMe(): ResponseType {
		return request("getMe", null)
	}

	fun logOut(): ResponseType {
		return request("logOut", null)
	}

	/**
	 * Use this method to close the botData instance before moving it from one local server to another. You need to delete the webhook before calling this method to ensure that the botData isn't launched again after server restart. The method will return error 429 in the first 10 minutes after the botData is launched. Returns True on success. Requires no parameters.
	 */
	fun close(): ResponseType {
		return request("close", null)
	}

	/**
	 * Use this method to forward messages of any kind. Service messages can't be forwarded. On success, the sent Message is returned.
	 * @param chatId    Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * @param fromChatId    Integer or String	Yes	Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
	 * @param disableNotification    Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * @param protectContent    Boolean	Optional	Protects the contents of the forwarded message from forwarding and saving
	 * @param messageId    Integer	Yes	Message identifier in the chat specified in from_chat_id
	 */
	open fun forwardMessage(chatId: String, fromChatId: String, messageId: Int, disableNotification: Boolean? = null, protectContent: Boolean? = null): ResponseType {
		val options = mutableQuery(5, "chat_id" to chatId, "from_chat_id" to fromChatId, "message_id" to messageId)
		options.putIfNotEmpty("disable_notification", disableNotification?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protectContent ?: defaultSendSettings.protectContent)
		return request("forwardMessage", options)
	}

	fun forwardMessage(chatId: Long, fromChatId: Long, messageId: Int, disableNotification: Boolean = false, protectContent: Boolean = false): ResponseType
		= forwardMessage(chatId.toString(), fromChatId.toString(), messageId, disableNotification, protectContent)

	/**
	 * Use this method to copy messages of any kind. Service messages and invoice messages can't be copied. The method is analogous to the method forwardMessage, but the copied message doesn't have a link to the original message.
	 *
	 * Returns the MessageId of the sent message on success.
	 *
	 * Parameter	Type	Required	Description
	 * @param chat_id	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * @param from_chat_id	Integer or String	Yes	Unique identifier for the chat where the original message was sent (or channel username in the format @channelusername)
	 * @param message_id	Integer	Yes	Message identifier in the chat specified in from_chat_id
	 * @param caption	String	Optional	New caption for media, 0-1024 characters after entities parsing. If not specified, the original caption is kept
	 * @param parse_mode	String	Optional	Mode for parsing entities in the new caption. See formatting options for more details.
	 * @param caption_entities	Array of MessageEntity	Optional	A JSON-serialized list of special entities that appear in the new caption, which can be specified instead of parse_mode
	 * @param disable_notification	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * @param protect_content	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * @param reply_to_message_id	Integer	Optional	If the message is a reply, ID of the original message
	 * @param allow_sending_without_reply	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * @param reply_markup	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	open fun copyMessage(chat_id: String, from_chat_id: String, message_id: Int, caption: String? = null, parse_mode: String? = null
		, caption_entities: List<MessageEntity>? = null, disable_notification: Boolean? = null, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null, reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id, "from_chat_id" to from_chat_id, "message_id" to message_id)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return request("copyMessage", options)
	}

	open fun copyMessage(chat_id: Long, from_chat_id: Long, message_id: Int, caption: String? = null, parse_mode: String? = null
					, caption_entities: List<MessageEntity>? = null, disable_notification: Boolean? = null, protect_content: Boolean? = null
					, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null, reply_markup: String? = null
	): ResponseType = copyMessage(chat_id.toString(), from_chat_id.toString(), message_id, caption, parse_mode, caption_entities, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	private fun MutableQuery.putIfNotEmpty(key: String, value: Boolean) { if (value) this[key] = value }
	private fun MutableQuery.putIfNotEmpty(key: String, value: Float) { if (value != 0f) this[key] = value }
	private fun MutableQuery.putIfNotEmpty(key: String, value: Double) { if (value != 0.0) this[key] = value }
	private fun MutableQuery.putIfNotEmpty(key: String, value: Int) { if (value != 0) this[key] = value }
	private fun MutableQuery.putIfNotEmpty(key: String, value: Long) { if (value != 0L) this[key] = value }
	private fun MutableQuery.putIfNotEmpty(key: String, value: String?) { if (!value.isNullOrEmpty()) this[key] = value }

	/**
	 * Use this method to send audio files, if you want Telegram clients to display them in the music player. Your audio must be in the .MP3 or .M4A format. On success, the sent Message is returned. Bots can currently send audio files of up to 50 MB in size, this limit may be changed in the future.
	 *
	 * For sending voice messages, use the sendVoice method instead.
	 *
	 * Parameter	Type	Required	Description
	 * chat_id	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * audio	InputFile or String	Yes	Audio file to send. Pass a file_id as String to send an audio file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get an audio file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
	 * caption	String	Optional	Audio caption, 0-1024 characters after entities parsing
	 * parse_mode	String	Optional	Mode for parsing entities in the audio caption. See formatting options for more details.
	 * caption_entities	Array of MessageEntity	Optional	A JSON-serialized list of special entities that appear in the caption, which can be specified instead of parse_mode
	 * duration	Integer	Optional	Duration of the audio in seconds
	 * performer	String	Optional	Performer
	 * title	String	Optional	Track name
	 * thumb	InputFile or String	Optional	Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
	 * disable_notification	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * protect_content	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * reply_to_message_id	Integer	Optional	If the message is a reply, ID of the original message
	 * allow_sending_without_reply	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * reply_markup	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	open fun sendAudio(chat_id: String, audio: BinaryData, caption: String? = null
		, parse_mode: String? = null
		, caption_entities: List<MessageEntity>? = null
		, duration: Int = 0
		, performer: String? = null
		, title: String? = null
		, thumb: BinaryData? = null
		, disable_notification: Boolean? = null
		, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean? = null
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("performer", performer)
		options.putIfNotEmpty("title", title)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)

		return requestUpload("sendAudio", options, mutableMapOf(
			"audio" to audio
		).also { if (thumb != null) it["thump"] = thumb }
		)
	}

	open fun sendAudio(chat_id: Long, audio: BinaryData, caption: String? = null
				  , parse_mode: String? = null
				  , caption_entities: List<MessageEntity>? = null
				  , duration: Int = 0
				  , performer: String? = null
				  , title: String? = null
				  , thumb: BinaryData? = null
				  , disable_notification: Boolean? = null
				  , protect_content: Boolean? = null
				  , reply_to_message_id: Int = 0
				  , allow_sending_without_reply: Boolean? = null
				  , reply_markup: String? = null
	): ResponseType = sendAudio(chat_id.toString(), audio, caption, parse_mode, caption_entities, duration, performer, title, thumb, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	open fun sendAudio(chat_id: String, audioId: String, caption: String? = null
				  , parse_mode: String? = null
				  , caption_entities: List<MessageEntity>? = null
				  , duration: Int = 0
				  , performer: String? = null
				  , title: String? = null
				  , thumb: String? = null
				  , disable_notification: Boolean? = null
				  , protect_content: Boolean? = null
				  , reply_to_message_id: Int = 0
				  , allow_sending_without_reply: Boolean? = null
				  , reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id, "audio" to audioId)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("performer", performer)
		options.putIfNotEmpty("title", title)
		options.putIfNotEmpty("thumb", thumb)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)

		return request("sendAudio", options)
	}

	fun sendAudio(chat_id: Long, audioId: String, caption: String? = null
				  , parse_mode: String? = null
				  , caption_entities: List<MessageEntity>? = null
				  , duration: Int = 0
				  , performer: String? = null
				  , title: String? = null
				  , thumb: String? = null
				  , disable_notification: Boolean? = null
				  , protect_content: Boolean? = null
				  , reply_to_message_id: Int = 0
				  , allow_sending_without_reply: Boolean? = null
				  , reply_markup: String? = null
	): ResponseType = sendAudio(chat_id.toString(), audioId, caption, parse_mode, caption_entities, duration, performer, title, thumb, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	/**
	 * Use this method to send general files. On success, the sent Message is returned. Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.

	 * Parameter	Type	Required	Description
	 * chat_id	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * document	InputFile or String	Yes	File to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
	 * thumb	InputFile or String	Optional	Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
	 * caption	String	Optional	Document caption (may also be used when resending documents by file_id), 0-1024 characters after entities parsing
	 * parse_mode	String	Optional	Mode for parsing entities in the document caption. See formatting options for more details.
	 * caption_entities	Array of MessageEntity	Optional	A JSON-serialized list of special entities that appear in the caption, which can be specified instead of parse_mode
	 * disable_content_type_detection	Boolean	Optional	Disables automatic server-side content type detection for files uploaded using multipart/form-data
	 * disable_notification	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * protect_content	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * reply_to_message_id	Integer	Optional	If the message is a reply, ID of the original message
	 * allow_sending_without_reply	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * reply_markup	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	fun sendDocument(chat_id: String, document: BinaryData
		, thumb: BinaryData? = null
		, caption: String? = null
		, parse_mode: String? = null
		, caption_entities: List<MessageEntity>? = null
		, disable_content_type_detection: Boolean = false
		, disable_notification: Boolean? = null
		, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean? = null
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("disable_content_type_detection", disable_content_type_detection)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return requestUpload("sendDocument", options, mutableMapOf("document" to document)
			.also { if (thumb != null) it["thump"] = thumb }
		)
	}

	fun sendDocument(chat_id: Long, document: BinaryData
		, thumb: BinaryData? = null
		, caption: String? = null
		, parse_mode: String? = null
		, caption_entities: List<MessageEntity>? = null
		, disable_content_type_detection: Boolean = false
		, disable_notification: Boolean? = null
		, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean? = null
		, reply_markup: String? = null
	): ResponseType = sendDocument(chat_id.toString(), document, thumb, caption, parse_mode, caption_entities, disable_content_type_detection, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	fun sendDocument(chat_id: String, document: String
		, caption: String? = null
		, parse_mode: String? = null
		, caption_entities: List<MessageEntity>? = null
		, disable_content_type_detection: Boolean = false
		, disable_notification: Boolean? = null
		, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean? = null
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id, "document" to document)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("disable_content_type_detection", disable_content_type_detection)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return request("sendDocument", options)
	}

	fun sendDocument(chat_id: Long, document: String
					 , caption: String? = null
					 , parse_mode: String? = null
					 , caption_entities: List<MessageEntity>? = null
					 , disable_content_type_detection: Boolean = false
					 , disable_notification: Boolean? = null
					 , protect_content: Boolean? = null
					 , reply_to_message_id: Int = 0
					 , allow_sending_without_reply: Boolean? = null
					 , reply_markup: String? = null
	): ResponseType = sendDocument(chat_id.toString(), document, caption, parse_mode, caption_entities, disable_content_type_detection, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	/**
	 * Use this method to send video files, Telegram clients support mp4 videos (other formats may be sent as Document). On success, the sent Message is returned. Bots can currently send video files of up to 50 MB in size, this limit may be changed in the future.

	 * Parameter	Type	Required	Description
	 *
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [video]	InputFile or String	Yes	Video to send. Pass a file_id as String to send a video that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a video from the Internet, or upload a new video using multipart/form-data. More info on Sending Files »
	 * * [duration]	Integer	Optional	Duration of sent video in seconds
	 * * [width]	Integer	Optional	Video width
	 * * [height]	Integer	Optional	Video height
	 * * [thumb]	InputFile or String	Optional	Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
	 * * [caption]	String	Optional	Video caption (may also be used when resending videos by file_id), 0-1024 characters after entities parsing
	 * * [parse_mode]	String	Optional	Mode for parsing entities in the video caption. See formatting options for more details.
	 * * [caption_entities]	Array of MessageEntity	Optional	A JSON-serialized list of special entities that appear in the caption, which can be specified instead of parse_mode
	 * * [supports_streaming]	Boolean	Optional	Pass True, if the uploaded video is suitable for streaming
	 * * [disable_notification]	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * * [protect_content]	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * * [reply_to_message_id]	Integer	Optional	If the message is a reply, ID of the original message
	 * * [allow_sending_without_reply]	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * * [reply_markup]	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	fun sendVideo(chat_id: String, video: BinaryData
		  , duration: Int = 0
		  , width: Int = 0
		  , height: Int = 0
		  , thumb: BinaryData? = null
		  , caption: String? = null
		  , parse_mode: String? = null
		  , caption_entities: List<MessageEntity>? = null
		  , supports_streaming: Boolean = false
		  , disable_notification: Boolean? = null
		  , protect_content: Boolean? = null
		  , reply_to_message_id: Int = 0
		  , allow_sending_without_reply: Boolean? = null
		  , reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("width", width)
		options.putIfNotEmpty("height", height)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("supports_streaming", supports_streaming)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return requestUpload("sendVideo", options, mutableMapOf("video" to video)
			.also { if (thumb != null) it["thump"] = thumb }
		)
	}

	fun sendVideo(chat_id: Long, video: BinaryData
				  , duration: Int = 0
				  , width: Int = 0
				  , height: Int = 0
				  , thumb: BinaryData? = null
				  , caption: String? = null
				  , parse_mode: String? = null
				  , caption_entities: List<MessageEntity>? = null
				  , supports_streaming: Boolean = false
				  , disable_notification: Boolean? = null
				  , protect_content: Boolean? = null
				  , reply_to_message_id: Int = 0
				  , allow_sending_without_reply: Boolean? = null
				  , reply_markup: String? = null
	): ResponseType = sendVideo(chat_id.toString(), video, duration, width, height, thumb, caption, parse_mode, caption_entities, supports_streaming, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	fun sendVideo(chat_id: String, video: String
				  , duration: Int = 0
				  , width: Int = 0
				  , height: Int = 0
				  , caption: String? = null
				  , parse_mode: String? = null
				  , caption_entities: List<MessageEntity>? = null
				  , supports_streaming: Boolean = false
				  , disable_notification: Boolean? = null
				  , protect_content: Boolean? = null
				  , reply_to_message_id: Int = 0
				  , allow_sending_without_reply: Boolean? = null
				  , reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id, "video" to video)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("width", width)
		options.putIfNotEmpty("height", height)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("supports_streaming", supports_streaming)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return request("sendVideo", options)
	}

	fun sendVideo(chat_id: Long, video: String
				  , duration: Int = 0
				  , width: Int = 0
				  , height: Int = 0
				  , caption: String? = null
				  , parse_mode: String? = null
				  , caption_entities: List<MessageEntity>? = null
				  , supports_streaming: Boolean = false
				  , disable_notification: Boolean? = null
				  , protect_content: Boolean? = null
				  , reply_to_message_id: Int = 0
				  , allow_sending_without_reply: Boolean? = null
				  , reply_markup: String? = null
	): ResponseType = sendVideo(chat_id.toString(), video, duration, width, height, caption, parse_mode, caption_entities, supports_streaming, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	/**
	 * Use this method to send animation files (GIF or H.264/MPEG-4 AVC video without sound). On success, the sent Message is returned. Bots can currently send animation files of up to 50 MB in size, this limit may be changed in the future.
	 *
	 * Parameter	Type	Required	Description
	 * chat_id	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * animation	InputFile or String	Yes	Animation to send. Pass a file_id as String to send an animation that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get an animation from the Internet, or upload a new animation using multipart/form-data. More info on Sending Files »
	 * duration	Integer	Optional	Duration of sent animation in seconds
	 * width	Integer	Optional	Animation width
	 * height	Integer	Optional	Animation height
	 * thumb	InputFile or String	Optional	Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
	 * caption	String	Optional	Animation caption (may also be used when resending animation by file_id), 0-1024 characters after entities parsing
	 * parse_mode	String	Optional	Mode for parsing entities in the animation caption. See formatting options for more details.
	 * caption_entities	Array of MessageEntity	Optional	A JSON-serialized list of special entities that appear in the caption, which can be specified instead of parse_mode
	 * disable_notification	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * protect_content	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * reply_to_message_id	Integer	Optional	If the message is a reply, ID of the original message
	 * allow_sending_without_reply	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * reply_markup	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	fun sendAnimation(chat_id: String, animation: BinaryData
		  , duration: Int = 0
		  , width: Int = 0
		  , height: Int = 0
		  , thumb: BinaryData? = null
		  , caption: String? = null
		  , parse_mode: String? = null
		  , caption_entities: List<MessageEntity>? = null
		  , disable_notification: Boolean? = null
		  , protect_content: Boolean? = null
		  , reply_to_message_id: Int = 0
		  , allow_sending_without_reply: Boolean? = null
		  , reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("width", width)
		options.putIfNotEmpty("height", height)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return requestUpload("sendAnimation", options, mutableMapOf("animation" to animation)
			.also { if (thumb != null) it["thump"] = thumb }
		)
	}

	fun sendAnimation(chat_id: Long, animation: BinaryData
					  , duration: Int = 0
					  , width: Int = 0
					  , height: Int = 0
					  , thumb: BinaryData? = null
					  , caption: String? = null
					  , parse_mode: String? = null
					  , caption_entities: List<MessageEntity>? = null
					  , disable_notification: Boolean? = null
					  , protect_content: Boolean? = null
					  , reply_to_message_id: Int = 0
					  , allow_sending_without_reply: Boolean? = null
					  , reply_markup: String? = null
	): ResponseType = sendAnimation(chat_id.toString(), animation, duration, width, height, thumb, caption, parse_mode, caption_entities, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	fun sendAnimation(chat_id: String, animation: String
					  , duration: Int = 0
					  , width: Int = 0
					  , height: Int = 0
					  , caption: String? = null
					  , parse_mode: String? = null
					  , caption_entities: List<MessageEntity>? = null
					  , disable_notification: Boolean? = null
					  , protect_content: Boolean? = null
					  , reply_to_message_id: Int = 0
					  , allow_sending_without_reply: Boolean? = null
					  , reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id, "animation" to animation)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("width", width)
		options.putIfNotEmpty("height", height)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return request("sendAnimation", options)
	}

	fun sendAnimation(chat_id: Long, animation: String
					  , duration: Int = 0
					  , width: Int = 0
					  , height: Int = 0
					  , caption: String? = null
					  , parse_mode: String? = null
					  , caption_entities: List<MessageEntity>? = null
					  , disable_notification: Boolean? = null
					  , protect_content: Boolean? = null
					  , reply_to_message_id: Int = 0
					  , allow_sending_without_reply: Boolean? = null
					  , reply_markup: String? = null
	): ResponseType = sendAnimation(chat_id.toString(), animation, duration, width, height, caption, parse_mode, caption_entities, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	/**
	 * Use this method to send audio files, if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .OGG file encoded with OPUS (other formats may be sent as Audio or Document). On success, the sent Message is returned. Bots can currently send voice messages of up to 50 MB in size, this limit may be changed in the future.
	 *
	 *
	 * Parameter	Type	Required	Description
	 *
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [voice]	InputFile or String	Yes	Audio file to send. Pass a file_id as String to send a file that exists on the Telegram servers (recommended), pass an HTTP URL as a String for Telegram to get a file from the Internet, or upload a new one using multipart/form-data. More info on Sending Files »
	 * * [caption]	String	Optional	Voice message caption, 0-1024 characters after entities parsing
	 * * [parse_mode]	String	Optional	Mode for parsing entities in the voice message caption. See formatting options for more details.
	 * * [caption_entities]	Array of MessageEntity	Optional	A JSON-serialized list of special entities that appear in the caption, which can be specified instead of parse_mode
	 * * [duration]	Integer	Optional	Duration of the voice message in seconds
	 * * [disable_notification]	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * * [protect_content]	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * * [reply_to_message_id]	Integer	Optional	If the message is a reply, ID of the original message
	 * * [allow_sending_without_reply]	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * * [reply_markup]	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	fun sendVoice(chat_id: String, voice: BinaryData
		  , caption: String? = null
		  , parse_mode: String? = null
		  , caption_entities: List<MessageEntity>? = null
		  , disable_notification: Boolean? = null
		  , protect_content: Boolean? = null
		  , reply_to_message_id: Int = 0
		  , allow_sending_without_reply: Boolean? = null
		  , reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return requestUpload("sendVoice", options, mutableMapOf("voice" to voice))
	}

	fun sendVoice(chat_id: Long, voice: BinaryData
		, caption: String? = null
		, parse_mode: String? = null
		, caption_entities: List<MessageEntity>? = null
		, disable_notification: Boolean? = null
		, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean? = null
		, reply_markup: String? = null
	): ResponseType = sendVoice(chat_id.toString(), voice, caption, parse_mode, caption_entities, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	fun sendVoice(chat_id: String, voice: String
		, caption: String? = null
		, parse_mode: String? = null
		, caption_entities: List<MessageEntity>? = null
		, disable_notification: Boolean? = null
		, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean? = null
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id, "voice" to voice)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode ?: defaultSendSettings.parseMode)
		options.putIfNotEmpty("caption_entities", caption_entities?.let(this::entities))
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return request("sendVoice", options)
	}

	fun sendVoice(chat_id: Long, voice: String
		, caption: String? = null
		, parse_mode: String? = null
		, caption_entities: List<MessageEntity>? = null
		, disable_notification: Boolean? = null
		, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean? = null
		, reply_markup: String? = null
	): ResponseType = sendVoice(chat_id.toString(), voice, caption, parse_mode, caption_entities, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	/**
	 * As of v.4.0, Telegram clients support rounded square mp4 videos of up to 1 minute long. Use this method to send video messages. On success, the sent Message is returned.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [video_note]	InputFile or String	Yes	Video note to send. Pass a file_id as String to send a video note that exists on the Telegram servers (recommended) or upload a new video using multipart/form-data. More info on Sending Files ». Sending video notes by a URL is currently unsupported
	 * * [duration]	Integer	Optional	Duration of sent video in seconds
	 * * [length]	Integer	Optional	Video width and height, i.e. diameter of the video message
	 * * [thumb]	InputFile or String	Optional	Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files »
	 * * [disable_notification]	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * * [protect_content]	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * * [reply_to_message_id]	Integer	Optional	If the message is a reply, ID of the original message
	 * * [allow_sending_without_reply]	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * * [reply_markup]	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	fun sendVideoNote(chat_id: String, video_note: BinaryData
		, duration: Int = 0
		, length: Int = 0
		, thumb: BinaryData? = null
		, disable_notification: Boolean? = null
		, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean? = null
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("length", length)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return requestUpload("sendVideoNote", options, mutableMapOf("video_note" to video_note)
			.also { if (thumb != null) it["thumb"] = thumb }
		)
	}

	fun sendVideoNote(chat_id: Long, video_note: BinaryData
					  , duration: Int = 0
					  , length: Int = 0
					  , thumb: BinaryData? = null
					  , disable_notification: Boolean? = null
					  , protect_content: Boolean? = null
					  , reply_to_message_id: Int = 0
					  , allow_sending_without_reply: Boolean? = null
					  , reply_markup: String? = null
	): ResponseType = sendVideoNote(chat_id.toString(), video_note, duration, length, thumb, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	fun sendVideoNote(chat_id: String, video_note: String
					  , duration: Int = 0
					  , length: Int = 0
					  , disable_notification: Boolean? = null
					  , protect_content: Boolean? = null
					  , reply_to_message_id: Int = 0
					  , allow_sending_without_reply: Boolean? = null
					  , reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id, "video_note" to video_note)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("length", length)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return request("sendVideoNote", options)
	}

	fun sendVideoNote(chat_id: Long, video_note: String
					  , duration: Int = 0
					  , length: Int = 0
					  , disable_notification: Boolean? = null
					  , protect_content: Boolean? = null
					  , reply_to_message_id: Int = 0
					  , allow_sending_without_reply: Boolean? = null
					  , reply_markup: String? = null
	): ResponseType = sendVideoNote(chat_id.toString(), video_note, duration, length, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	/**
	 * Use this method to set a new profile photo for the chat. Photos can't be changed for private chats. The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [photo]	InputFile	Yes	New chat photo, uploaded using multipart/form-data
	 */
	fun setChatPhoto(chat_id: String, photo: BinaryData): ResponseType {
		return requestUpload("setChatPhoto", query("chat_id", chat_id), mapOf("photo" to photo))
	}

	fun setChatPhoto(chat_id: Long, photo: BinaryData): ResponseType {
		return setChatPhoto(chat_id.toString(), photo)
	}

	/**
	 * Use this method to delete a chat photo. Photos can't be changed for private chats. The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 */
	fun deleteChatPhoto(chat_id: String): ResponseType {
		return request("deleteChatPhoto", query("chat_id", chat_id))
	}

	fun deleteChatPhoto(chat_id: Long): ResponseType
		= deleteChatPhoto(chat_id.toString())

	/**
	 * Use this method to change the title of a chat. Titles can't be changed for private chats. The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [title]	String	Yes	New chat title, 1-255 characters
	 */
	fun setChatTitle(chat_id: String, title: String): ResponseType {
		return request("setChatTitle", query("chat_id" to chat_id, "title" to title))
	}

	fun setChatTitle(chat_id: Long, title: String): ResponseType
		= setChatTitle(chat_id.toString(), title)

	/**
	 * Use this method to change the description of a group, a supergroup or a channel. The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [description]	String	Optional	New chat description, 0-255 characters
	 */
	fun setChatDescription(chat_id: String, description: String? = null): ResponseType {
		return request("setChatDescription", query("chat_id" to chat_id, "description" to description))
	}

	fun setChatDescription(chat_id: Long, description: String? = null): ResponseType
		= setChatDescription(chat_id.toString(), description)

	/**
	 * Use this method to add a message to the list of pinned messages in a chat. If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' administrator right in a supergroup or 'can_edit_messages' administrator right in a channel. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [message_id]	Integer	Yes	Identifier of a message to pin
	 * * [disable_notification]	Boolean	Optional	Pass True, if it is not necessary to send a notification to all chat members about the new pinned message. Notifications are always disabled in channels and private chats.
	 */
	fun pinChatMessage(chat_id: String, message_id: Int, disable_notification: Boolean? = null): ResponseType {
		val options = mutableQuery(3, "chat_id" to chat_id, "message_id" to message_id)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		return request("pinChatMessage", options)
	}


	fun pinChatMessage(chat_id: Long, message_id: Int, disable_notification: Boolean? = null): ResponseType
		= pinChatMessage(chat_id.toString(), message_id, disable_notification)

	/**
	 * Use this method to remove a message from the list of pinned messages in a chat. If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' administrator right in a supergroup or 'can_edit_messages' administrator right in a channel. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [message_id]	Integer	Optional	Identifier of a message to unpin. If not specified, the most recent pinned message (by sending date) will be unpinned.
	 */
	fun unpinChatMessage(chat_id: String, message_id: Int): ResponseType {
		val options = query("chat_id" to chat_id, "message_id" to message_id)
		return request("unpinChatMessage", options)
	}

	fun unpinChatMessage(chat_id: Long, message_id: Int): ResponseType
			= unpinChatMessage(chat_id.toString(), message_id)

	/**
	 * Use this method to clear the list of pinned messages in a chat. If the chat is not a private chat, the bot must be an administrator in the chat for this to work and must have the 'can_pin_messages' administrator right in a supergroup or 'can_edit_messages' administrator right in a channel. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 */
	fun unpinAllChatMessages(chat_id: String): ResponseType {
		return request("unpinAllChatMessages", query("chat_id", chat_id))
	}

	fun unpinAllChatMessages(chat_id: Long) : ResponseType = unpinAllChatMessages(chat_id.toString())

	/**
	 * Use this method for your bot to leave a group, supergroup or channel. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target supergroup or channel (in the format @channelusername)
	 */
	fun leaveChat(chat_id: String): ResponseType {
		return request("leaveChat", query("chat_id", chat_id))
	}

	fun leaveChat(chat_id: Long): ResponseType  = leaveChat(chat_id.toString())

	/**
	 * Use this method to promote or demote a user in a supergroup or a channel. The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights. Pass False for all boolean parameters to demote a user. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [user_id]	Integer	Yes	Unique identifier of the target user
	 * * [is_anonymous]	Boolean	Optional	Pass True, if the administrator's presence in the chat is hidden
	 * * [can_manage_chat]	Boolean	Optional	Pass True, if the administrator can access the chat event log, chat statistics, message statistics in channels, see channel members, see anonymous administrators in supergroups and ignore slow mode. Implied by any other administrator privilege
	 * * [can_post_messages]	Boolean	Optional	Pass True, if the administrator can create channel posts, channels only
	 * * [can_edit_messages]	Boolean	Optional	Pass True, if the administrator can edit messages of other users and can pin messages, channels only
	 * * [can_delete_messages]	Boolean	Optional	Pass True, if the administrator can delete messages of other users
	 * * [can_manage_voice_chats]	Boolean	Optional	Pass True, if the administrator can manage voice chats
	 * * [can_restrict_members]	Boolean	Optional	Pass True, if the administrator can restrict, ban or unban chat members
	 * * [can_promote_members]	Boolean	Optional	Pass True, if the administrator can add new administrators with a subset of their own privileges or demote administrators that he has promoted, directly or indirectly (promoted by administrators that were appointed by him)
	 * * [can_change_info]	Boolean	Optional	Pass True, if the administrator can change chat title, photo and other settings
	 * * [can_invite_users]	Boolean	Optional	Pass True, if the administrator can invite new users to the chat
	 * * [can_pin_messages]	Boolean	Optional	Pass True, if the administrator can pin messages, supergroups only
	 */
	fun promoteChatMember(chat_id: String, user_id: Long
		, is_anonymous: Boolean? = null
		, can_manage_chat: Boolean? = null
		, can_post_messages: Boolean? = null
		, can_edit_messages: Boolean? = null
		, can_delete_messages: Boolean? = null
		, can_manage_voice_chats: Boolean? = null
		, can_restrict_members: Boolean? = null
		, can_promote_members: Boolean? = null
		, can_change_info: Boolean? = null
		, can_invite_users: Boolean? = null
		, can_pin_messages: Boolean? = null
	): ResponseType {
		val options = mutableQuery()
		options["chat_id"] = chat_id
		options["user_id"] = user_id
		is_anonymous?.apply { options["is_anonymous"] = is_anonymous }
		can_manage_chat?.apply { options["can_manage_chat"] = can_manage_chat }
		can_post_messages?.apply { options["can_post_messages"] = can_post_messages }
		can_edit_messages?.apply { options["can_edit_messages"] = can_edit_messages }
		can_delete_messages?.apply { options["can_delete_messages"] = can_delete_messages }
		can_manage_voice_chats?.apply { options["can_manage_voice_chats"] = can_manage_voice_chats }
		can_restrict_members?.apply { options["can_restrict_members"] = can_restrict_members }
		can_promote_members?.apply { options["can_promote_members"] = can_promote_members }
		can_change_info?.apply { options["can_change_info"] = can_change_info }
		can_invite_users?.apply { options["can_invite_users"] = can_invite_users }
		can_pin_messages?.apply { options["can_pin_messages"] = can_pin_messages }
		return request("promoteChatMember", options)
	}
	
	class MemberRights(var is_anonymous: Boolean? = null
		   , var can_manage_chat: Boolean? = null
		   , var can_post_messages: Boolean? = null
		   , var can_edit_messages: Boolean? = null
		   , var can_delete_messages: Boolean? = null
		   , var can_manage_voice_chats: Boolean? = null
		   , var can_restrict_members: Boolean? = null
		   , var can_promote_members: Boolean? = null
		   , var can_change_info: Boolean? = null
		   , var can_invite_users: Boolean? = null
		   , var can_pin_messages: Boolean? = null
	) {
		companion object {
			val AllFalse = MemberRights(false, false, false, false, false, false, false, false, false, false, false)
			val AllTrue = MemberRights(true,true,true,true,true,true,true,true,true,true,true)
		}
	}

	fun promoteChatMember(chat_id: String, user_id: Long, rights: MemberRights): ResponseType {
		return promoteChatMember(chat_id, user_id
			, rights.is_anonymous
			, rights.can_manage_chat
			, rights.can_post_messages
			, rights.can_edit_messages
			, rights.can_delete_messages
			, rights.can_manage_voice_chats
			, rights.can_restrict_members
			, rights.can_promote_members
			, rights.can_change_info
			, rights.can_invite_users
			, rights.can_pin_messages
		)
	}

	fun promoteChatMember(chat_id: Long, user_id: Long, rights: MemberRights): ResponseType
		= promoteChatMember(chat_id.toString(), user_id, rights)

	/**
	 * Use this method to set a custom title for an administrator in a supergroup promoted by the bot. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
	 * * [user_id]	Integer	Yes	Unique identifier of the target user
	 * * [custom_title]	String	Yes	New custom title for the administrator; 0-16 characters, emoji are not allowed
	 */
	fun setChatAdministratorCustomTitle(chat_id: String, user_id: Long, custom_title: String): ResponseType {
		return request("setChatAdministratorCustomTitle", query("chat_id" to chat_id, "user_id" to user_id, "custom_title" to custom_title))
	}

	fun setChatAdministratorCustomTitle(chat_id: Long, user_id: Long, custom_title: String): ResponseType
		= setChatAdministratorCustomTitle(chat_id.toString(), user_id, custom_title)

	/**
	 * Use this method to ban a channel chat in a supergroup or a channel. Until the chat is unbanned, the owner of the banned chat won't be able to send messages on behalf of any of their channels. The bot must be an administrator in the supergroup or channel for this to work and must have the appropriate administrator rights. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [sender_chat_id]	Integer	Yes	Unique identifier of the target sender chat
	 */
	fun banChatSenderChat(chat_id: String, sender_chat_id: Long): ResponseType {
		return request("banChatSenderChat", query("chat_id" to chat_id, "sender_chat_id" to sender_chat_id))
	}

	fun banChatSenderChat(chat_id: Long, sender_chat_id: Long): ResponseType
		= banChatSenderChat(chat_id.toString(), sender_chat_id)

	/**
	 * Use this method to unban a previously banned channel chat in a supergroup or channel. The bot must be an administrator for this to work and must have the appropriate administrator rights. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [sender_chat_id]	Integer	Yes	Unique identifier of the target sender chat
	 */
	fun unbanChatSenderChat(chat_id: String, sender_chat_id: Long): ResponseType {
		return request("unbanChatSenderChat", query("chat_id" to chat_id, "sender_chat_id" to sender_chat_id))
	}

	fun unbanChatSenderChat(chat_id: Long, sender_chat_id: Long): ResponseType
			= unbanChatSenderChat(chat_id.toString(), sender_chat_id)

	/**
	 * Use this method to set default chat permissions for all members. The bot must be an administrator in the group or a supergroup for this to work and must have the can_restrict_members administrator rights. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
	 * [permissions]	ChatPermissions	Yes	A JSON-serialized object for new default chat permissions
	 */
	fun setChatPermissions(chat_id: String, permissions: String): ResponseType {
		return request("setChatPermissions", query("chat_id" to chat_id, "permissions" to permissions))
	}

	fun setChatPermissions(chat_id: Long, permissions: String): ResponseType
		= setChatPermissions(chat_id.toString(), permissions)

	/**
	 * Use this method to send a group of photos, videos, documents or audios as an album. Documents and audio files can be only grouped in an album with messages of the same type. On success, an array of Messages that were sent is returned.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [media]	Array of InputMediaAudio, InputMediaDocument, InputMediaPhoto and InputMediaVideo	Yes	A JSON-serialized array describing messages to be sent, must include 2-10 items
	 * * [disable_notification]	Boolean	Optional	Sends messages silently. Users will receive a notification with no sound.
	 * * [protect_content]	Boolean	Optional	Protects the contents of the sent messages from forwarding and saving
	 * * [reply_to_message_id]	Integer	Optional	If the messages are a reply, ID of the original message
	 * * [allow_sending_without_reply]	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 */
	fun sendMediaGroup(chat_id: String, media: List<InputMedia>, disable_notification: Boolean? = null, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null
	): ResponseType {

		var i = 0
		val dataToUpload = mutableListOf<BinaryData>()
		for (m in media) {
			m.data?.apply {
				i++
				fileName = fileName?.let {
					"$i." + ext(it)
				} ?: i.toString()
				m.media = "attach://$fileName"
				dataToUpload += this
			}

			when (m) {
				is InputMediaThumbable -> {
					m.thumbData?.apply {
						i++
						fileName = fileName?.let {
							"$i." + ext(it)
						} ?: i.toString()
						m.thumb = "attach://$fileName"
						dataToUpload += this
					}
				}
			}
		}

		val options = mutableQuery("chat_id" to chat_id, "media" to jsonMediaList(media))
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)

		return if (dataToUpload.isEmpty())
			request("sendMediaGroup", options)
		else
			requestUpload("sendMediaGroup", options, dataToUpload.associateBy { it.fileName!! })
	}

	private fun ext(fileName: String): String {
		val ind = fileName.lastIndexOf('.')
		if (ind == -1) return ""
		if (ind + 1 == fileName.length) return ""
		return fileName.substring(ind + 1)
	}

	fun sendMediaGroup(chat_id: String, disable_notification: Boolean? = null, protect_content: Boolean? = null
					   , reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null, media: MediaGroupBuilder.() -> Unit
	): ResponseType {
		return sendMediaGroup(chat_id, MediaGroupBuilder().build(media), disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply)
	}

	fun sendMediaGroup(chat_id: Long, disable_notification: Boolean? = null, protect_content: Boolean? = null
					   , reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null, media: MediaGroupBuilder.() -> Unit
	): ResponseType {
		return sendMediaGroup(chat_id.toString(), MediaGroupBuilder().build(media), disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply)
	}

	fun sendMediaGroup(chat_id: Long, media: List<InputMedia>, disable_notification: Boolean? = null, protect_content: Boolean? = null
					   , reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null
	) = sendMediaGroup(chat_id.toString(), media, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply)


	/**
	 * Use this method to send point on the map. On success, the sent Message is returned.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [latitude]	Float number	Yes	Latitude of the location
	 * * [longitude]	Float number	Yes	Longitude of the location
	 * * [horizontal_accuracy]	Float number	Optional	The radius of uncertainty for the location, measured in meters; 0-1500
	 * * [live_period]	Integer	Optional	Period in seconds for which the location will be updated (see Live Locations, should be between 60 and 86400.
	 * * [heading]	Integer	Optional	For live locations, a direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
	 * * [proximity_alert_radius]	Integer	Optional	For live locations, a maximum distance for proximity alerts about approaching another chat member, in meters. Must be between 1 and 100000 if specified.
	 * * [disable_notification]	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * * [protect_content]	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * * [reply_to_message_id]	Integer	Optional	If the message is a reply, ID of the original message
	 * * [allow_sending_without_reply]	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * * [reply_markup]	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	fun sendLocation(chat_id: String, latitude: Float, longitude: Float, horizontal_accuracy: Float = 0f, live_period: Int = 0
		, heading: Int = 0, proximity_alert_radius: Int = 0, disable_notification: Boolean? = null, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null, reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery()
		options["chat_id"] = chat_id
		options["latitude"] = latitude
		options["longitude"] = longitude
		options.putIfNotEmpty("horizontal_accuracy", horizontal_accuracy)
		options.putIfNotEmpty("live_period", live_period)
		options.putIfNotEmpty("heading", heading)
		options.putIfNotEmpty("proximity_alert_radius", proximity_alert_radius)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		return request("sendLocation", options)
	}

	fun sendLocation(chat_id: Long, latitude: Float, longitude: Float, horizontal_accuracy: Float = 0f, live_period: Int = 0
					 , heading: Int = 0, proximity_alert_radius: Int, disable_notification: Boolean? = null, protect_content: Boolean? = null
					 , reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null, reply_markup: String? = null
	): ResponseType = sendLocation(chat_id.toString(), latitude, longitude, horizontal_accuracy, live_period, heading, proximity_alert_radius, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	/**
	 * Use this method to edit live location messages. A location can be edited until its live_period expires or editing is explicitly disabled by a call to stopMessageLiveLocation. On success, if the edited message is not an inline message, the edited Message is returned, otherwise True is returned.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Optional	Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [message_id]	Integer	Optional	Required if inline_message_id is not specified. Identifier of the message to edit
	 * * [inline_message_id]	String	Optional	Required if chat_id and message_id are not specified. Identifier of the inline message
	 * * [latitude]	Float number	Yes	Latitude of new location
	 * * [longitude]	Float number	Yes	Longitude of new location
	 * * [horizontal_accuracy]	Float number	Optional	The radius of uncertainty for the location, measured in meters; 0-1500
	 * * [heading]	Integer	Optional	Direction in which the user is moving, in degrees. Must be between 1 and 360 if specified.
	 * * [proximity_alert_radius]	Integer	Optional	Maximum distance for proximity alerts about approaching another chat member, in meters. Must be between 1 and 100000 if specified.
	 * * [reply_markup]	InlineKeyboardMarkup	Optional	A JSON-serialized object for a new inline keyboard.
	 */
	private fun _editMessageLiveLocation(chat_id: String? = null, message_id: Int = 0, inline_message_id: String? = null, latitude: Float, longitude: Float, horizontal_accuracy: Float = 0f
								 , heading: Int = 0, proximity_alert_radius: Int, reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery()

		chat_id?.apply {
			if (message_id == 0) throw IllegalArgumentException("message_id is not defined")
			options["chat_id"] = chat_id
			options["message_id"] = message_id
		} ?: inline_message_id?.apply {
			options["inline_message_id"] = chat_id
		} ?: throw IllegalArgumentException("Neither chat_id+message_id nor inline_message_id were set")

		options["latitude"] = latitude
		options["longitude"] = longitude
		options.putIfNotEmpty("horizontal_accuracy", horizontal_accuracy)
		options.putIfNotEmpty("heading", heading)
		options.putIfNotEmpty("proximity_alert_radius", proximity_alert_radius)
		options.putIfNotEmpty("reply_markup", reply_markup)
		return request("editMessageLiveLocation", options)
	}

	fun editMessageLiveLocation(chat_id: String, message_id: Int, latitude: Float, longitude: Float, horizontal_accuracy: Float = 0f
								, heading: Int = 0, proximity_alert_radius: Int, reply_markup: String? = null
	): ResponseType = _editMessageLiveLocation(chat_id, message_id, null, latitude, longitude, horizontal_accuracy, heading, proximity_alert_radius, reply_markup)

	fun editMessageLiveLocation(chat_id: Long, message_id: Int, latitude: Float, longitude: Float, horizontal_accuracy: Float = 0f
								, heading: Int = 0, proximity_alert_radius: Int, reply_markup: String? = null
	): ResponseType = _editMessageLiveLocation(chat_id.toString(), message_id, null, latitude, longitude, horizontal_accuracy, heading, proximity_alert_radius, reply_markup)

	fun editMessageLiveLocation(inline_message_id: String, latitude: Float, longitude: Float, horizontal_accuracy: Float = 0f
								, heading: Int = 0, proximity_alert_radius: Int, reply_markup: String? = null
	): ResponseType = _editMessageLiveLocation(null, 0, inline_message_id, latitude, longitude, horizontal_accuracy, heading, proximity_alert_radius, reply_markup)

	/**
	 * Use this method to stop updating a live location message before live_period expires. On success, if the message is not an inline message, the edited Message is returned, otherwise True is returned.
	 *
	 * Parameter	Type	Required	Description
	 * * chat_id	Integer or String	Optional	Required if inline_message_id is not specified. Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * message_id	Integer	Optional	Required if inline_message_id is not specified. Identifier of the message with live location to stop
	 * * inline_message_id	String	Optional	Required if chat_id and message_id are not specified. Identifier of the inline message
	 * * reply_markup	InlineKeyboardMarkup	Optional	A JSON-serialized object for a new inline keyboard.
	 */

	fun stopMessageLiveLocation(chat_id: String, message_id: Int, reply_markup: String? = null): ResponseType {
		val options = mutableQuery(3, "chat_id" to chat_id, "message_id" to message_id)
		options.putIfNotEmpty("reply_markup", reply_markup)
		return request("stopMessageLiveLocation", options)
	}
	fun stopMessageLiveLocation(inline_message_id: String, reply_markup: String? = null): ResponseType {
		val options = mutableQuery("inline_message_id" to inline_message_id)
		options.putIfNotEmpty("reply_markup", reply_markup)
		return request("stopMessageLiveLocation", options)
	}

	/**
	 * Use this method to send information about a venue. On success, the sent Message is returned.
	 *
	 * Parameter	Type	Required	Description
	 * * chat_id	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * latitude	Float number	Yes	Latitude of the venue
	 * * longitude	Float number	Yes	Longitude of the venue
	 * * title	String	Yes	Name of the venue
	 * * address	String	Yes	Address of the venue
	 * * foursquare_id	String	Optional	Foursquare identifier of the venue
	 * * foursquare_type	String	Optional	Foursquare type of the venue, if known. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium” or “food/icecream”.)
	 * * google_place_id	String	Optional	Google Places identifier of the venue
	 * * google_place_type	String	Optional	Google Places type of the venue. (See supported types.)
	 * * disable_notification	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * * protect_content	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * * reply_to_message_id	Integer	Optional	If the message is a reply, ID of the original message
	 * * allow_sending_without_reply	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * * reply_markup	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	fun sendVenue(chat_id: String, latitude: Float, longitude: Float, title: String, address: String
		, foursquare_id: String? = null, foursquare_type: String? = null, disable_notification: Boolean? = null
		, protect_content: Boolean? = null, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id, "latitude" to latitude, "longitude" to longitude
			, "title" to title, "address" to address
		)
		options.putIfNotEmpty("foursquare_id", foursquare_id)
		options.putIfNotEmpty("foursquare_type", foursquare_type)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		return request("sendVenue", options)
	}

	fun sendVenue(chat_id: Long, latitude: Float, longitude: Float, title: String, address: String
				  , foursquare_id: String? = null, foursquare_type: String? = null, disable_notification: Boolean? = null
				  , protect_content: Boolean? = null, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null
				  , reply_markup: String? = null
	): ResponseType = sendVenue(chat_id.toString(), latitude, longitude, title, address, foursquare_id, foursquare_type, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	/**
	 * sendContact
	 * Use this method to send phone contacts. On success, the sent Message is returned.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [phone_number]	String	Yes	Contact's phone number
	 * * [first_name]	String	Yes	Contact's first name
	 * * [last_name]	String	Optional	Contact's last name
	 * * [vcard]	String	Optional	Additional data about the contact in the form of a vCard, 0-2048 bytes
	 * * [disable_notification]	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * * [protect_content]	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * * [reply_to_message_id]	Integer	Optional	If the message is a reply, ID of the original message
	 * * [allow_sending_without_reply]	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * * [reply_markup]	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove keyboard or to force a reply from the user.
	 */
	fun sendContact(chat_id: String, phone_number: String, first_name: String, last_name: String? = null
		, vcard: String? = null, disable_notification: Boolean? = null, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null, reply_markup: String? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id, "phone_number" to phone_number
			, "first_name" to first_name
		)
		options.putIfNotEmpty("last_name", last_name)
		options.putIfNotEmpty("vcard", vcard)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		return request("sendVenue", options)
	}

	fun sendContact(chat_id: Long, phone_number: String, first_name: String, last_name: String? = null
					, vcard: String? = null, disable_notification: Boolean? = null, protect_content: Boolean? = null
					, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null, reply_markup: String? = null
	): ResponseType = sendContact(chat_id.toString(), phone_number, first_name, last_name, vcard, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	/**
	 * Use this method to send a native poll. On success, the sent Message is returned.
	 *
	 * Parameter	Type	Required	Description
	 * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * [question]	String	Yes	Poll question, 1-300 characters
	 * [options]	Array of String	Yes	A JSON-serialized list of answer options, 2-10 strings 1-100 characters each
	 * [is_anonymous]	Boolean	Optional	True, if the poll needs to be anonymous, defaults to True
	 * [type]	String	Optional	Poll type, “quiz” or “regular”, defaults to “regular”
	 * [allows_multiple_answers]	Boolean	Optional	True, if the poll allows multiple answers, ignored for polls in quiz mode, defaults to False
	 * [correct_option_id]	Integer	Optional	0-based identifier of the correct answer option, required for polls in quiz mode
	 * [explanation]	String	Optional	Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200 characters with at most 2 line feeds after entities parsing
	 * [explanation_parse_mode]	String	Optional	Mode for parsing entities in the explanation. See formatting options for more details.
	 * [explanation_entities]	Array of MessageEntity	Optional	A JSON-serialized list of special entities that appear in the poll explanation, which can be specified instead of parse_mode
	 * [open_period]	Integer	Optional	Amount of time in seconds the poll will be active after creation, 5-600. Can't be used together with close_date.
	 * [close_date]	Integer	Optional	Point in time (Unix timestamp) when the poll will be automatically closed. Must be at least 5 and no more than 600 seconds in the future. Can't be used together with open_period.
	 * [is_closed]	Boolean	Optional	Pass True, if the poll needs to be immediately closed. This can be useful for poll preview.
	 * [disable_notification]	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * [protect_content]	Boolean	Optional	Protects the contents of the sent message from forwarding and saving
	 * [reply_to_message_id]	Integer	Optional	If the message is a reply, ID of the original message
	 * [allow_sending_without_reply]	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * [reply_markup]	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	fun sendPoll(chat_id: String, question: String, options: List<String>, is_anonymous: Boolean = true, type: String? = "regular"
		, allows_multiple_answers: Boolean = false, correct_option_id: Int = -1, explanation: String? = null
		, explanation_parse_mode: String? = null, explanation_entities: List<MessageEntity>? = null
		, open_period: Int = 0, close_date: Int = 0, is_closed: Boolean = false, disable_notification: Boolean? = null
		, protect_content: Boolean? = null, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null
		, reply_markup: ReplyMarkup? = null
	): ResponseType {
		val options = mutableQuery("chat_id" to chat_id, "question" to question
			, "options" to jsonifier.array2JsonString(options)
		)
		if (!is_anonymous)
			options.set("is_anonymous", is_anonymous)
		options.putIfNotEmpty("type", type)
		options.putIfNotEmpty("allows_multiple_answers", allows_multiple_answers)
		if (correct_option_id > -1)
			options["correct_option_id"] = correct_option_id
		options.putIfNotEmpty("explanation", explanation)
		options.putIfNotEmpty("explanation_parse_mode", explanation_parse_mode)
		explanation_entities?.apply { options["explanation_entities"] = jsonifier.entities(this) }

		options.putIfNotEmpty("open_period", open_period)
		options.putIfNotEmpty("close_date", close_date)
		options.putIfNotEmpty("is_closed", is_closed)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		reply_markup?.apply {
			options["reply_markup"] =jsonifier.replyMarkup(this)
		}

		return request("sendPoll", options)
	}

	fun sendPoll(chat_id: Long, question: String, options: List<String>, is_anonymous: Boolean = true, type: String? = "regular"
				 , allows_multiple_answers: Boolean = false, correct_option_id: Int = -1, explanation: String? = null
				 , explanation_parse_mode: String? = null, explanation_entities: List<MessageEntity>? = null
				 , open_period: Int = 0, close_date: Int = 0, is_closed: Boolean = false, disable_notification: Boolean? = null
				 , protect_content: Boolean? = null, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null
				 , reply_markup: ReplyMarkup? = null
	): ResponseType = sendPoll(chat_id.toString(), question, options, is_anonymous, type, allows_multiple_answers
		, correct_option_id, explanation, explanation_parse_mode, explanation_entities, open_period, close_date, is_closed
		, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup
	)

	/**
	 * Use this method to send an animated emoji that will display a random value. On success, the sent Message is returned.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [emoji]	String	Optional	Emoji on which the dice throw animation is based. Currently, must be one of “🎲”, “🎯”, “🏀”, “⚽”, “🎳”, or “🎰”. Dice can have values 1-6 for “🎲”, “🎯” and “🎳”, values 1-5 for “🏀” and “⚽”, and values 1-64 for “🎰”. Defaults to “🎲”
	 * * [disable_notification]	Boolean	Optional	Sends the message silently. Users will receive a notification with no sound.
	 * * [protect_content]	Boolean	Optional	Protects the contents of the sent message from forwarding
	 * * [reply_to_message_id]	Integer	Optional	If the message is a reply, ID of the original message
	 * * [allow_sending_without_reply]	Boolean	Optional	Pass True, if the message should be sent even if the specified replied-to message is not found
	 * * [reply_markup]	InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply	Optional	Additional interface options. A JSON-serialized object for an inline keyboard, custom reply keyboard, instructions to remove reply keyboard or to force a reply from the user.
	 */
	fun sendDice(chat_id: String, emoji: String? = null, disable_notification: Boolean? = null, protect_content: Boolean? = null
		, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null, reply_markup: ReplyMarkup? = null
	): ResponseType {
		val options = mutableQuery(7, "chat_id" to chat_id)
		options.putIfNotEmpty("emoji", emoji)
		options.putIfNotEmpty("disable_notification", disable_notification ?: defaultSendSettings.disableNotification)
		options.putIfNotEmpty("protect_content", protect_content ?: defaultSendSettings.protectContent)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply ?: defaultSendSettings.allowSendingWithoutReply)
		reply_markup?.apply {
			options["reply_markup"] =jsonifier.replyMarkup(this)
		}

		return request("sendDice", options)
	}

	fun sendDice(chat_id: Long, emoji: String? = null, disable_notification: Boolean? = null, protect_content: Boolean? = null
				 , reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean? = null, reply_markup: ReplyMarkup? = null
	): ResponseType = sendDice(chat_id.toString(), emoji, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	/**
	 * Use this method when you need to tell the user that something is happening on the bot's side. The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status). Returns True on success.
	 *
	 * Example: The ImageBot needs some time to process a request and upload the image. Instead of sending a text message along the lines of “Retrieving image, please wait…”, the bot may use sendChatAction with action = upload_photo. The user will see a “sending photo” status for the bot.
	 *
	 * We only recommend using this method when a response from the bot will take a noticeable amount of time to arrive.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [action]	String	Yes	Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages, upload_photo for photos, record_video or upload_video for videos, record_voice or upload_voice for voice notes, upload_document for general files, choose_sticker for stickers, find_location for location data, record_video_note or upload_video_note for video notes.
	 */
	fun sendChatAction(chat_id: String, action: String): ResponseType {
		return request("sendChatAction", query("chat_id" to chat_id, "action" to action))
	}

	fun sendChatAction(chat_id: Long, action: String): ResponseType
		= sendChatAction(chat_id.toString(), action)

	/**
	 * Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
	 *
	 * Parameter	Type	Required	Description
	 * * user_id	Integer	Yes	Unique identifier of the target user
	 * * offset	Integer	Optional	Sequential number of the first photo to be returned. By default, all photos are returned.
	 * * limit	Integer	Optional	Limits the number of photos to be retrieved. Values between 1-100 are accepted. Defaults to 100.
	 */
	fun getUserProfilePhotos(user_id: Long, offset: Int = 0, limit: Int = 0): ResponseType {
		val options = mutableQuery(3, "user_id" to user_id)
		options.putIfNotEmpty("offset", offset)
		options.putIfNotEmpty("limit", limit)
		return request("getUserProfilePhotos", options)
	}

	/**
	 * Use this method to generate a new primary invite link for a chat; any previously generated primary link is revoked. The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights. Returns the new invite link as String on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * Note: Each administrator in a chat generates their own invite links. Bots can't use invite links generated by other administrators. If you want your bot to work with invite links, it will need to generate its own link using exportChatInviteLink or by calling the getChat method. If your bot needs to generate a new primary invite link replacing its previous one, use exportChatInviteLink again.
	 */
	fun exportChatInviteLink(chat_id: String): ResponseType {
		return request("exportChatInviteLink", query("chat_id", chat_id))
	}

	fun exportChatInviteLink(chat_id: Long): ResponseType
		= exportChatInviteLink(chat_id.toString())


	/**
	 * Use this method to create an additional invite link for a chat. The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights. The link can be revoked using the method revokeChatInviteLink. Returns the new invite link as ChatInviteLink object.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [name]	String	Optional	Invite link name; 0-32 characters
	 * * [expire_date]	Integer	Optional	Point in time (Unix timestamp) when the link will expire
	 * * [member_limit]	Integer	Optional	Maximum number of users that can be members of the chat simultaneously after joining the chat via this invite link; 1-99999
	 * * [creates_join_request]	Boolean	Optional	True, if users joining the chat via the link need to be approved by chat administrators. If True, member_limit can't be specified
	 */
	fun createChatInviteLink(chat_id: String, name: String? = null, expire_date: Int = 0, member_limit: Int = 0, creates_join_request: Boolean = false): ResponseType {
		val options = mutableQuery(5, "chat_id" to chat_id)
		options.putIfNotEmpty("name", name)
		options.putIfNotEmpty("expire_date", expire_date)
		options.putIfNotEmpty("member_limit", member_limit)
		options.putIfNotEmpty("creates_join_request", creates_join_request)
		return request("createChatInviteLink", options)
	}

	fun createChatInviteLink(chat_id: Long, name: String? = null, expire_date: Int = 0, member_limit: Int = 0, creates_join_request: Boolean = false): ResponseType
		= createChatInviteLink(chat_id.toString(), name, expire_date, member_limit, creates_join_request)

	/**
	 * Use this method to edit a non-primary invite link created by the bot. The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights. Returns the edited invite link as a ChatInviteLink object.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [invite_link]	String	Yes	The invite link to edit
	 * * [name]	String	Optional	Invite link name; 0-32 characters
	 * * [expire_date]	Integer	Optional	Point in time (Unix timestamp) when the link will expire
	 * * [member_limit]	Integer	Optional	Maximum number of users that can be members of the chat simultaneously after joining the chat via this invite link; 1-99999
	 * * [creates_join_request]	Boolean	Optional	True, if users joining the chat via the link need to be approved by chat administrators. If True, member_limit can't be specified
	 */
	fun editChatInviteLink(chat_id: String, invite_link: String, name: String? = null, expire_date: Int = 0, member_limit: Int = 0, creates_join_request: Boolean = false): ResponseType {
		val options = mutableQuery(6, "chat_id" to chat_id, "invite_link" to invite_link)
		options.putIfNotEmpty("name", name)
		options.putIfNotEmpty("expire_date", expire_date)
		options.putIfNotEmpty("member_limit", member_limit)
		options.putIfNotEmpty("creates_join_request", creates_join_request)
		return request("editChatInviteLink", options)
	}

	fun editChatInviteLink(chat_id: Long, invite_link: String, name: String? = null, expire_date: Int = 0, member_limit: Int = 0, creates_join_request: Boolean = false): ResponseType
		= editChatInviteLink(chat_id.toString(), invite_link, name, expire_date, member_limit, creates_join_request)

	/**
	 * Use this method to revoke an invite link created by the bot. If the primary link is revoked, a new link is automatically generated. The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights. Returns the revoked invite link as ChatInviteLink object.
	 *
	 * Parameter	Type	Required	Description
	 * [chat_id]	Integer or String	Yes	Unique identifier of the target chat or username of the target channel (in the format @channelusername)
	 * [invite_link]	String	Yes	The invite link to revoke
	 */
	fun revokeChatInviteLink(chat_id: String, invite_link: String): ResponseType {
		return request("revokeChatInviteLink", query("chat_id" to chat_id, "invite_link" to invite_link))
	}

	fun revokeChatInviteLink(chat_id: Long, invite_link: String): ResponseType
		= revokeChatInviteLink(chat_id.toString(), invite_link)

	/**
	 * Use this method to approve a chat join request. The bot must be an administrator in the chat for this to work and must have the can_invite_users administrator right. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [user_id]	Integer	Yes	Unique identifier of the target user
	 */
	fun approveChatJoinRequest(chat_id: String, user_id: Long): ResponseType {
		return request("approveChatJoinRequest", query("chat_id" to chat_id, "user_id" to user_id))
	}

	fun approveChatJoinRequest(chat_id: Long, user_id: Long): ResponseType
		= approveChatJoinRequest(chat_id.toString(), user_id)

	/**
	 * Use this method to decline a chat join request. The bot must be an administrator in the chat for this to work and must have the can_invite_users administrator right. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * [chat_id]	Integer or String	Yes	Unique identifier for the target chat or username of the target channel (in the format @channelusername)
	 * * [user_id]	Integer	Yes	Unique identifier of the target user
	 */
	fun declineChatJoinRequest(chat_id: String, user_id: Long): ResponseType {
		return request("declineChatJoinRequest", query("chat_id" to chat_id, "user_id" to user_id))
	}

	fun declineChatJoinRequest(chat_id: Long, user_id: Long): ResponseType
		= declineChatJoinRequest(chat_id.toString(), user_id)

	/**
	 * Use this method to set a new group sticker set for a supergroup. The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights. Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * chat_id	Integer or String	Yes	Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
	 * * sticker_set_name	String	Yes	Name of the sticker set to be set as the group sticker set
	 */
	fun setChatStickerSet(chat_id: String, sticker_set_name: String): ResponseType {
		return request("setChatStickerSet", query("chat_id" to chat_id, "sticker_set_name" to sticker_set_name))
	}

	fun setChatStickerSet(chat_id: Long, sticker_set_name: String): ResponseType
		= setChatStickerSet(chat_id.toString(), sticker_set_name)

	/**
	 * Use this method to delete a group sticker set from a supergroup. The bot must be an administrator in the chat for this to work and must have the appropriate administrator rights. Use the field can_set_sticker_set optionally returned in getChat requests to check if the bot can use this method. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * chat_id	Integer or String	Yes	Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
	 */
	fun deleteChatStickerSet(chat_id: String): ResponseType {
		return request("deleteChatStickerSet", query("chat_id", chat_id))
	}

	fun deleteChatStickerSet(chat_id: Long): ResponseType
		= deleteChatStickerSet(chat_id.toString())

	/**
	 * Use this method to change the list of the bot's commands. See https://core.telegram.org/bots#commands for more details about bot commands. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * [commands]	Array of BotCommand	Yes	A JSON-serialized list of bot commands to be set as the list of the bot's commands. At most 100 commands can be specified.
	 * [scope]	BotCommandScope	Optional	A JSON-serialized object, describing scope of users for which the commands are relevant. Defaults to BotCommandScopeDefault.
	 * [language_code]	String	Optional	A two-letter ISO 639-1 language code. If empty, commands will be applied to all users from the given scope, for whose language there are no dedicated commands
	 */
	fun setMyCommands(commands: String, scope: String? = null, language_code: String? = null): ResponseType {
		val options = mutableQuery(3, "commands" to commands)
		options.putIfNotEmpty("scope", scope)
		options.putIfNotEmpty("language_code", language_code)
		return request("setMyCommands", options)
	}

	/**
	 * Use this method to delete the list of the bot's commands for the given scope and user language. After deletion, higher level commands will be shown to affected users. Returns True on success.
	 *
	 * Parameter	Type	Required	Description
	 * * scope	BotCommandScope	Optional	A JSON-serialized object, describing scope of users for which the commands are relevant. Defaults to BotCommandScopeDefault.
	 * * language_code	String	Optional	A two-letter ISO 639-1 language code. If empty, commands will be applied to all users from the given scope, for whose language there are no dedicated commands
	 */
	fun deleteMyCommands(scope: String? = null, language_code: String? = null): ResponseType {
		val options = mutableQuery(2)
		options.putIfNotEmpty("scope", scope)
		options.putIfNotEmpty("language_code", language_code)
		return request("setMyCommands", options)
	}

	/**
	 * Use this method to get the current list of the bot's commands for the given scope and user language. Returns Array of BotCommand on success. If commands aren't set, an empty list is returned.
	 *
	 * Parameter	Type	Required	Description
	 * * scope	BotCommandScope	Optional	A JSON-serialized object, describing scope of users. Defaults to BotCommandScopeDefault.
	 * * language_code	String	Optional	A two-letter ISO 639-1 language code or an empty string
	 */
	fun getMyCommands(scope: String? = null, language_code: String? = null): ResponseType {
		val options = mutableQuery(2)
		options.putIfNotEmpty("scope", scope)
		options.putIfNotEmpty("language_code", language_code)
		return request("getMyCommands", options)
	}
}