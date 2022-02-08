@file:Suppress("MemberVisibilityCanBePrivate")

package iris.tg.api

import iris.tg.api.items.ReplyMarkup
import iris.tg.api.items.MessageEntity
import iris.tg.connection.Connection.BinaryData
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

typealias Options = Map<String, Any?>
typealias MutableOptions = MutableMap<String, Any?>
typealias HashOptions = HashMap<String, Any?>


interface Jsonifier {
	fun entities(entities: List<MessageEntity>): String
	fun replyMarkup(replyMarkup: ReplyMarkup): String
	fun array2JsonString(array: Array<String>): String
}

abstract class TgApiAbstract<ResponseType>(protected val token: String, apiPath: String? = null): Jsonifier {

	private val apiPath = apiPath ?: "https://api.telegram.org"

	private val urlCache = HashMap<String, String>()

	var jsonifier: Jsonifier = JsonifierSimple()

	override fun entities(entities: List<MessageEntity>) = jsonifier.entities(entities)

	override fun replyMarkup(replyMarkup: ReplyMarkup) = jsonifier.replyMarkup(replyMarkup)

	open fun json(replyMarkup: ReplyMarkup) = jsonifier.replyMarkup(replyMarkup)

	open fun json(entities: List<MessageEntity>) = jsonifier.entities(entities)

	open fun json(array: Array<String>): String = jsonifier.array2JsonString(array)

	override fun array2JsonString(array: Array<String>): String = jsonifier.array2JsonString(array)

	fun request(method: String, options: Options?): ResponseType {
		val url = urlCache.getOrPut(method) { "$apiPath/bot${this.token}/$method" }
		return requestImpl(url, method, options)
	}

	protected abstract fun requestImpl(url: String, method: String, options: Options?): ResponseType
	protected abstract fun requestUploadImpl(url: String, method: String, files: Map<String, BinaryData>, options: Options?): ResponseType

	fun requestUpload(method: String, options: Options?, files: Map<String, BinaryData>): ResponseType {
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
	open fun sendMessage(chat_id: String, text: String, parse_mode: String? = null, entities: String? = null, disable_web_page_preview: Boolean = false,
		disable_notification: Boolean = false, protect_content: Boolean = false, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean = false,
		reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id, "text" to text)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("entities", entities)
		options.putIfNotEmpty("disable_web_page_preview", disable_web_page_preview)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		return request("sendMessage", options)
	}

	open fun sendMessage(chat_id: Long, text: String, parse_mode: String? = null, entities: String? = null, disable_web_page_preview: Boolean = false,
						 disable_notification: Boolean = false, protect_content: Boolean = false, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean = false,
						 reply_markup: String? = null
	): ResponseType = sendMessage(chat_id.toString(), text, parse_mode, entities, disable_web_page_preview, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	/*open fun createOptions(options: Options?) : MutableOptions {
		if (options == null) return HashMap()
		return (options as? MutableOptions)?.let {
			if (options.size == 1) // singleton mutablemap bug fix
				null
			else
				options
		} ?: HashMap(options)
	}*/

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
		parse_mode: String? = null, entities: String? = null, disable_web_page_preview: Boolean = false, reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>()
		options.putIfNotEmpty("chat_id", chat_id)
		options.putIfNotEmpty("message_id", message_id)
		options.putIfNotEmpty("inline_message_id", inline_message_id)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("entities", entities)
		options.putIfNotEmpty("disable_web_page_preview", disable_web_page_preview)
		options.putIfNotEmpty("reply_markup", reply_markup)

		options["text"] = text
		return request("editMessageText", options)
	}

	open fun editMessageText(chat_id: String?, message_id: Int, text: String
		, parse_mode: String? = null, entities: String? = null, disable_web_page_preview: Boolean = false, reply_markup: String? = null
	): ResponseType = editMessageText(text, chat_id, message_id, null, parse_mode, entities, disable_web_page_preview, reply_markup)

	open fun editMessageText(chat_id: Long, message_id: Int, text: String
		, parse_mode: String? = null, entities: String? = null, disable_web_page_preview: Boolean = false, reply_markup: String? = null
	): ResponseType = editMessageText(text, chat_id.toString(), message_id, null, parse_mode, entities, disable_web_page_preview, reply_markup)

	open fun editMessageText(inline_message_id: String, text: String
		, parse_mode: String? = null, entities: String? = null, disable_web_page_preview: Boolean = false, reply_markup: String? = null
	): ResponseType = editMessageText(text, null, 0, inline_message_id, parse_mode, entities, disable_web_page_preview, reply_markup)

	fun chatId(chat_id: Long): String = chat_id.toString()
	fun chatId(chat_id: String): String = chat_id

	open fun getUpdates(offset: Long, timeout: Int = 0, allowedTypes: String? = null): ResponseType {
		val options = HashOptions()
		if (offset != 0L)
			options["offset"] = offset
		if (timeout != 0)
			options["timeout"] = timeout
		if (allowedTypes != null)
			options["allowed_updates"] = allowedTypes
		return request("getUpdates", options)
	}

	open fun getChatMember(chatId: String, userId: Long): ResponseType {
		return request("getChatMember", mapOf("chat_id" to chatId, "user_id" to userId))
	}

	open fun getChatMember(chatId: Long, userId: Long): ResponseType = getChatMember(chatId.toString(), userId)

	open fun getChatAdministrators(chatId: String): ResponseType {
		return request("getChatAdministrators", mapOf("chat_id" to chatId))
	}

	open fun getChatAdministrators(chatId: Long): ResponseType = getChatAdministrators(chatId.toString())

	open fun getChatMemberCount(chatId: String): ResponseType {
		return request("getChatMemberCount", mapOf("chat_id" to chatId))
	}

	open fun getChatMemberCount(chatId: Long): ResponseType = getChatMemberCount(chatId.toString())

	open fun banChatMember(chatId: String, userId: Long, untilDate: Int = 0): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chatId, "user_id" to userId)
		if (untilDate != 0)
			options["until_date"] = untilDate
		return request("banChatMember", options)
	}

	open fun banChatMember(chatId: Long, userId: Long, untilDate: Int = 0): ResponseType = banChatMember(chatId.toString(), userId, untilDate)

	open fun unbanChatMember(chatId: String, userId: Long): ResponseType {
		return request("unbanChatMember", mapOf("chat_id" to chatId, "user_id" to userId))
	}

	open fun unbanChatMember(chatId: Long, userId: Long): ResponseType  = unbanChatMember(chatId.toString(), userId)

	open fun getChat(chatId: String): ResponseType {
		return request("getChat", mapOf("chat_id" to chatId))
	}

	open fun getChat(chatId: Long): ResponseType = getChat(chatId.toString())

	open fun deleteMessage(chatId: String, localId: Int): ResponseType {
		return request("deleteMessage", mapOf("chat_id" to chatId, "message_id" to localId))
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
	open fun sendPhoto(chat_id: String, file: BinaryData, caption: String? = null, parse_mode: String? = null, caption_entities: String? = null
		, disable_notification: Boolean = false, protect_content: Boolean = false, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean = false
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		return requestUpload("sendPhoto", options, mapOf("photo" to file))
	}

	open fun sendPhoto(chat_id: Long, file: BinaryData, caption: String? = null, parse_mode: String? = null, caption_entities: String? = null
	   , disable_notification: Boolean = false, protect_content: Boolean = false, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean = false
	   , reply_markup: String? = null
	): ResponseType = sendPhoto(chat_id.toString(), file, caption, parse_mode, caption_entities, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	open fun sendPhoto(chat_id: String, fileId: String, caption: String? = null, parse_mode: String? = null, caption_entities: String? = null
	   , disable_notification: Boolean = false, protect_content: Boolean = false, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean = false
	   , reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		return request("sendPhoto", options)
	}

	open fun sendPhoto(chat_id: Long, fileId: String, caption: String? = null, parse_mode: String? = null, caption_entities: String? = null
					   , disable_notification: Boolean = false, protect_content: Boolean = false, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean = false
					   , reply_markup: String? = null
	): ResponseType = sendPhoto(chat_id, fileId, caption, parse_mode, caption_entities, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	open fun getFile(fileId: String): ResponseType {
		return request("getFile", mapOf("file_id" to fileId))
	}

	open fun getPath(filePath: String): String {
		return "$apiPath/file/bot$token/$filePath"
	}

	open fun answerCallbackQuery(callbackQueryId: String, text: String? = null, showAlert: Boolean = false, url: String? = null, cacheTime: Int = 0): ResponseType {
		val options = mutableMapOf<String, Any?>("callback_query_id" to callbackQueryId)
		if (text != null) options["text"] = text
		if (showAlert) options["show_alert"] = showAlert
		if (url != null) options["url"] = url
		if (cacheTime != 0) options["cache_time"] = cacheTime
		return request("answerCallbackQuery", options)
	}

	open fun restrictChatMember(chatId: String, userId: Long, permissions: String, untilDate: Int): ResponseType {
		val options = mapOf<String, Any?>("chat_id" to chatId, "user_id" to userId, "permissions" to permissions, "until_date" to untilDate)
		return request("restrictChatMember", options)
	}

	open fun restrictChatMember(chatId: Long, userId: Long, permissions: String, untilDate: Int): ResponseType
		= restrictChatMember(chatId.toString(), userId, permissions, untilDate)

	open fun setWebhook(url: String, certificate: BinaryData? = null, ipAddress: String? = null, maxConnections: Int = 100, allowedUpdates: Array<String>? = null, dropPendingUpdates: Boolean = false): ResponseType {
		val options = mutableMapOf<String, Any?>("url" to url, "max_connections" to maxConnections)

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
		return request("deleteWebhook", if (dropPendingUpdates) mapOf("drop_pending_updates" to dropPendingUpdates) else null)
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
	open fun forwardMessage(chatId: String, fromChatId: String, messageId: Int, disableNotification: Boolean = false, protectContent: Boolean = false): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chatId, "from_chat_id" to fromChatId, "message_id" to messageId)
		options.putIfNotEmpty("disable_notification", disableNotification)
		options.putIfNotEmpty("protect_content", protectContent)
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
		, caption_entities: String? = null, disable_notification: Boolean = false, protect_content: Boolean = false
		, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean = false, reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id, "from_chat_id" to from_chat_id, "message_id" to message_id)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return request("copyMessage", options)
	}

	open fun copyMessage(chat_id: Long, from_chat_id: Long, message_id: Int, caption: String? = null, parse_mode: String? = null
					, caption_entities: String? = null, disable_notification: Boolean = false, protect_content: Boolean = false
					, reply_to_message_id: Int = 0, allow_sending_without_reply: Boolean = false, reply_markup: String? = null
	): ResponseType = copyMessage(chat_id.toString(), from_chat_id.toString(), message_id, caption, parse_mode, caption_entities, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	private fun MutableMap<String, Any?>.putIfNotEmpty(key: String, value: Boolean) { if (value) this[key] = value }
	private fun MutableMap<String, Any?>.putIfNotEmpty(key: String, value: Int) { if (value != 0) this[key] = value }
	private fun MutableMap<String, Any?>.putIfNotEmpty(key: String, value: String?) { if (!value.isNullOrEmpty()) this[key] = value }

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
		, caption_entities: String? = null
		, duration: Int = 0
		, performer: String? = null
		, title: String? = null
		, thumb: BinaryData? = null
		, disable_notification: Boolean = false
		, protect_content: Boolean = false
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean = false
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("performer", performer)
		options.putIfNotEmpty("title", title)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
		options.putIfNotEmpty("reply_markup", reply_markup)

		return requestUpload("sendAudio", options, mutableMapOf(
			"audio" to audio
		).also { if (thumb != null) it["thump"] = thumb }
		)
	}

	open fun sendAudio(chat_id: Long, audio: BinaryData, caption: String? = null
				  , parse_mode: String? = null
				  , caption_entities: String? = null
				  , duration: Int = 0
				  , performer: String? = null
				  , title: String? = null
				  , thumb: BinaryData? = null
				  , disable_notification: Boolean = false
				  , protect_content: Boolean = false
				  , reply_to_message_id: Int = 0
				  , allow_sending_without_reply: Boolean = false
				  , reply_markup: String? = null
	): ResponseType = sendAudio(chat_id.toString(), audio, caption, parse_mode, caption_entities, duration, performer, title, thumb, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	open fun sendAudio(chat_id: String, audioId: String, caption: String? = null
				  , parse_mode: String? = null
				  , caption_entities: String? = null
				  , duration: Int = 0
				  , performer: String? = null
				  , title: String? = null
				  , thumb: String? = null
				  , disable_notification: Boolean = false
				  , protect_content: Boolean = false
				  , reply_to_message_id: Int = 0
				  , allow_sending_without_reply: Boolean = false
				  , reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id, "audio" to audioId)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("performer", performer)
		options.putIfNotEmpty("title", title)
		options.putIfNotEmpty("thumb", thumb)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
		options.putIfNotEmpty("reply_markup", reply_markup)

		return request("sendAudio", options)
	}

	fun sendAudio(chat_id: Long, audioId: String, caption: String? = null
				  , parse_mode: String? = null
				  , caption_entities: String? = null
				  , duration: Int = 0
				  , performer: String? = null
				  , title: String? = null
				  , thumb: String? = null
				  , disable_notification: Boolean = false
				  , protect_content: Boolean = false
				  , reply_to_message_id: Int = 0
				  , allow_sending_without_reply: Boolean = false
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
		, caption_entities: String? = null
		, disable_content_type_detection: Boolean = false
		, disable_notification: Boolean = false
		, protect_content: Boolean = false
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean = false
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("disable_content_type_detection", disable_content_type_detection)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
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
		, caption_entities: String? = null
		, disable_content_type_detection: Boolean = false
		, disable_notification: Boolean = false
		, protect_content: Boolean = false
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean = false
		, reply_markup: String? = null
	): ResponseType = sendDocument(chat_id.toString(), document, thumb, caption, parse_mode, caption_entities, disable_content_type_detection, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	fun sendDocument(chat_id: String, document: String
		, caption: String? = null
		, parse_mode: String? = null
		, caption_entities: String? = null
		, disable_content_type_detection: Boolean = false
		, disable_notification: Boolean = false
		, protect_content: Boolean = false
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean = false
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id, "document" to document)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("disable_content_type_detection", disable_content_type_detection)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return request("sendDocument", options)
	}

	fun sendDocument(chat_id: Long, document: String
					 , caption: String? = null
					 , parse_mode: String? = null
					 , caption_entities: String? = null
					 , disable_content_type_detection: Boolean = false
					 , disable_notification: Boolean = false
					 , protect_content: Boolean = false
					 , reply_to_message_id: Int = 0
					 , allow_sending_without_reply: Boolean = false
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
		  , caption_entities: String? = null
		  , supports_streaming: Boolean = false
		  , disable_notification: Boolean = false
		  , protect_content: Boolean = false
		  , reply_to_message_id: Int = 0
		  , allow_sending_without_reply: Boolean = false
		  , reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("width", width)
		options.putIfNotEmpty("height", height)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("supports_streaming", supports_streaming)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
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
				  , caption_entities: String? = null
				  , supports_streaming: Boolean = false
				  , disable_notification: Boolean = false
				  , protect_content: Boolean = false
				  , reply_to_message_id: Int = 0
				  , allow_sending_without_reply: Boolean = false
				  , reply_markup: String? = null
	): ResponseType = sendVideo(chat_id.toString(), video, duration, width, height, thumb, caption, parse_mode, caption_entities, supports_streaming, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	fun sendVideo(chat_id: String, video: String
				  , duration: Int = 0
				  , width: Int = 0
				  , height: Int = 0
				  , caption: String? = null
				  , parse_mode: String? = null
				  , caption_entities: String? = null
				  , supports_streaming: Boolean = false
				  , disable_notification: Boolean = false
				  , protect_content: Boolean = false
				  , reply_to_message_id: Int = 0
				  , allow_sending_without_reply: Boolean = false
				  , reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id, "video" to video)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("width", width)
		options.putIfNotEmpty("height", height)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("supports_streaming", supports_streaming)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
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
				  , caption_entities: String? = null
				  , supports_streaming: Boolean = false
				  , disable_notification: Boolean = false
				  , protect_content: Boolean = false
				  , reply_to_message_id: Int = 0
				  , allow_sending_without_reply: Boolean = false
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
		  , caption_entities: String? = null
		  , disable_notification: Boolean = false
		  , protect_content: Boolean = false
		  , reply_to_message_id: Int = 0
		  , allow_sending_without_reply: Boolean = false
		  , reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("width", width)
		options.putIfNotEmpty("height", height)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
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
					  , caption_entities: String? = null
					  , disable_notification: Boolean = false
					  , protect_content: Boolean = false
					  , reply_to_message_id: Int = 0
					  , allow_sending_without_reply: Boolean = false
					  , reply_markup: String? = null
	): ResponseType = sendAnimation(chat_id.toString(), animation, duration, width, height, thumb, caption, parse_mode, caption_entities, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	fun sendAnimation(chat_id: String, animation: String
					  , duration: Int = 0
					  , width: Int = 0
					  , height: Int = 0
					  , caption: String? = null
					  , parse_mode: String? = null
					  , caption_entities: String? = null
					  , disable_notification: Boolean = false
					  , protect_content: Boolean = false
					  , reply_to_message_id: Int = 0
					  , allow_sending_without_reply: Boolean = false
					  , reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id, "animation" to animation)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("width", width)
		options.putIfNotEmpty("height", height)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
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
					  , caption_entities: String? = null
					  , disable_notification: Boolean = false
					  , protect_content: Boolean = false
					  , reply_to_message_id: Int = 0
					  , allow_sending_without_reply: Boolean = false
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
		  , caption_entities: String? = null
		  , disable_notification: Boolean = false
		  , protect_content: Boolean = false
		  , reply_to_message_id: Int = 0
		  , allow_sending_without_reply: Boolean = false
		  , reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return requestUpload("sendVoice", options, mutableMapOf("voice" to voice))
	}

	fun sendVoice(chat_id: Long, voice: BinaryData
		, caption: String? = null
		, parse_mode: String? = null
		, caption_entities: String? = null
		, disable_notification: Boolean = false
		, protect_content: Boolean = false
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean = false
		, reply_markup: String? = null
	): ResponseType = sendVoice(chat_id.toString(), voice, caption, parse_mode, caption_entities, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	fun sendVoice(chat_id: String, voice: String
		, caption: String? = null
		, parse_mode: String? = null
		, caption_entities: String? = null
		, disable_notification: Boolean = false
		, protect_content: Boolean = false
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean = false
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id, "voice" to voice)
		options.putIfNotEmpty("caption", caption)
		options.putIfNotEmpty("parse_mode", parse_mode)
		options.putIfNotEmpty("caption_entities", caption_entities)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return request("sendVoice", options)
	}

	fun sendVoice(chat_id: Long, voice: String
		, caption: String? = null
		, parse_mode: String? = null
		, caption_entities: String? = null
		, disable_notification: Boolean = false
		, protect_content: Boolean = false
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean = false
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
		, disable_notification: Boolean = false
		, protect_content: Boolean = false
		, reply_to_message_id: Int = 0
		, allow_sending_without_reply: Boolean = false
		, reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("length", length)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
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
					  , disable_notification: Boolean = false
					  , protect_content: Boolean = false
					  , reply_to_message_id: Int = 0
					  , allow_sending_without_reply: Boolean = false
					  , reply_markup: String? = null
	): ResponseType = sendVideoNote(chat_id.toString(), video_note, duration, length, thumb, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)

	fun sendVideoNote(chat_id: String, video_note: String
					  , duration: Int = 0
					  , length: Int = 0
					  , disable_notification: Boolean = false
					  , protect_content: Boolean = false
					  , reply_to_message_id: Int = 0
					  , allow_sending_without_reply: Boolean = false
					  , reply_markup: String? = null
	): ResponseType {
		val options = mutableMapOf<String, Any?>("chat_id" to chat_id, "video_note" to video_note)
		options.putIfNotEmpty("duration", duration)
		options.putIfNotEmpty("length", length)
		options.putIfNotEmpty("disable_notification", disable_notification)
		options.putIfNotEmpty("protect_content", protect_content)
		options.putIfNotEmpty("allow_sending_without_reply", allow_sending_without_reply)
		options.putIfNotEmpty("reply_markup", reply_markup)
		options.putIfNotEmpty("reply_to_message_id", reply_to_message_id)

		return request("sendVideoNote", options)
	}

	fun sendVideoNote(chat_id: Long, video_note: String
					  , duration: Int = 0
					  , length: Int = 0
					  , disable_notification: Boolean = false
					  , protect_content: Boolean = false
					  , reply_to_message_id: Int = 0
					  , allow_sending_without_reply: Boolean = false
					  , reply_markup: String? = null
	): ResponseType = sendVideoNote(chat_id.toString(), video_note, duration, length, disable_notification, protect_content, reply_to_message_id, allow_sending_without_reply, reply_markup)


	/*sendMediaGroup
	sendLocation
	editMessageLiveLocation
	stopMessageLiveLocation
	sendVenue
	sendContact
	sendPoll
	sendDice
	sendChatAction
	getUserProfilePhotos
	banChatMember
	unbanChatMember
	restrictChatMember
	promoteChatMember
	setChatAdministratorCustomTitle
	banChatSenderChat
	unbanChatSenderChat
	setChatPermissions
	exportChatInviteLink
	createChatInviteLink
	editChatInviteLink
	revokeChatInviteLink
	approveChatJoinRequest
	declineChatJoinRequest
	setChatPhoto
	deleteChatPhoto
	setChatTitle
	setChatDescription
	pinChatMessage
	unpinChatMessage
	unpinAllChatMessages
	leaveChat
	getChatAdministrators
	getChatMemberCount
	getChatMember
	setChatStickerSet
	deleteChatStickerSet
	answerCallbackQuery
	setMyCommands
	deleteMyCommands
	getMyCommands*/
}