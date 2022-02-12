package iris.tg.api

import iris.tg.api.items.MessageEntity
import iris.tg.api.response.*
import iris.tg.connection.Connection

open class TgApiObject(token: String, responseHandler: ResponseHandler<TgResponse>, apiPath: String? = null, connection: Connection<String, ByteArray?>? = null
) : TgApi<TgResponse>(token, responseHandler, apiPath, connection) {


	override fun sendMessage(
		chat_id: String,
		text: String,
		parse_mode: String?,
		entities: List<MessageEntity>?,
		disable_web_page_preview: Boolean?,
		disable_notification: Boolean?,
		protect_content: Boolean?,
		reply_to_message_id: Int,
		allow_sending_without_reply: Boolean?,
		reply_markup: String?
	): SendMessageResponse {
		return super.sendMessage(
			chat_id,
			text,
			parse_mode,
			entities,
			disable_web_page_preview,
			disable_notification,
			protect_content,
			reply_to_message_id,
			allow_sending_without_reply,
			reply_markup
		).cast()
	}

	override fun sendMessage(
		chat_id: Long,
		text: String,
		parse_mode: String?,
		entities: List<MessageEntity>?,
		disable_web_page_preview: Boolean?,
		disable_notification: Boolean?,
		protect_content: Boolean?,
		reply_to_message_id: Int,
		allow_sending_without_reply: Boolean?,
		reply_markup: String?
	): SendMessageResponse {
		return super.sendMessage(
			chat_id,
			text,
			parse_mode,
			entities,
			disable_web_page_preview,
			disable_notification,
			protect_content,
			reply_to_message_id,
			allow_sending_without_reply,
			reply_markup
		).cast()
	}

	override fun getUpdates(offset: Long, limit: Int, timeout: Int, allowedUpdates: AllowedUpdates?): GetUpdatesResponse {
		return super.getUpdates(offset, limit, timeout, allowedUpdates).cast()
	}

	override fun getChatMember(chatId: Long, userId: Long): GetChatMemberResponse {
		return super.getChatMember(chatId, userId).cast()
	}

	override fun getChatAdministrators(chatId: Long): GetChatAdministratorsResponse {
		return super.getChatAdministrators(chatId).cast()
	}


	override fun getChatMemberCount(chatId: Long): GetChatMemberCountResponse {
		return super.getChatMemberCount(chatId).cast()
	}

	override fun banChatMember(chatId: Long, userId: Long, untilDate: Int): BanChatMemberResponse {
		return super.banChatMember(chatId, userId, untilDate).cast()
	}

	override fun unbanChatMember(chatId: Long, userId: Long): BooleanResponse {
		return super.unbanChatMember(chatId, userId).cast()
	}

	override fun getChat(chatId: Long): GetChatResponse {
		return super.getChat(chatId).cast()
	}

	override fun getChat(chatId: String): GetChatResponse {
		return super.getChat(chatId).cast()
	}

	override fun deleteMessage(chatId: Long, localId: Int): BooleanResponse {
		return super.deleteMessage(chatId, localId).cast()
	}

	override fun sendPhoto(
		chat_id: String,
		file: Connection.BinaryData,
		caption: String?,
		parse_mode: String?,
		caption_entities: List<MessageEntity>?,
		disable_notification: Boolean?,
		protect_content: Boolean?,
		reply_to_message_id: Int,
		allow_sending_without_reply: Boolean?,
		reply_markup: String?
	): SendMessageResponse {
		return super.sendPhoto(
			chat_id,
			file,
			caption,
			parse_mode,
			caption_entities,
			disable_notification,
			protect_content,
			reply_to_message_id,
			allow_sending_without_reply,
			reply_markup
		).cast()
	}

	override fun sendPhoto(
		chat_id: Long,
		file: Connection.BinaryData,
		caption: String?,
		parse_mode: String?,
		caption_entities: List<MessageEntity>?,
		disable_notification: Boolean?,
		protect_content: Boolean?,
		reply_to_message_id: Int,
		allow_sending_without_reply: Boolean?,
		reply_markup: String?
	): SendMessageResponse {
		return super.sendPhoto(
			chat_id,
			file,
			caption,
			parse_mode,
			caption_entities,
			disable_notification,
			protect_content,
			reply_to_message_id,
			allow_sending_without_reply,
			reply_markup
		).cast()
	}

	override fun sendPhoto(
		chat_id: String,
		fileId: String,
		caption: String?,
		parse_mode: String?,
		caption_entities: List<MessageEntity>?,
		disable_notification: Boolean?,
		protect_content: Boolean?,
		reply_to_message_id: Int,
		allow_sending_without_reply: Boolean?,
		reply_markup: String?
	): SendMessageResponse {
		return super.sendPhoto(
			chat_id,
			fileId,
			caption,
			parse_mode,
			caption_entities,
			disable_notification,
			protect_content,
			reply_to_message_id,
			allow_sending_without_reply,
			reply_markup
		).cast()
	}

	override fun sendPhoto(
		chat_id: Long,
		fileId: String,
		caption: String?,
		parse_mode: String?,
		caption_entities: List<MessageEntity>?,
		disable_notification: Boolean?,
		protect_content: Boolean?,
		reply_to_message_id: Int,
		allow_sending_without_reply: Boolean?,
		reply_markup: String?
	): SendMessageResponse {
		return super.sendPhoto(
			chat_id,
			fileId,
			caption,
			parse_mode,
			caption_entities,
			disable_notification,
			protect_content,
			reply_to_message_id,
			allow_sending_without_reply,
			reply_markup
		).cast()
	}

	override fun getFile(fileId: String): GetFileResponse {
		return super.getFile(fileId).cast()
	}

	override fun answerCallbackQuery(callbackQueryId: String, text: String?, showAlert: Boolean, url: String?, cacheTime: Int): BooleanResponse {
		return super.answerCallbackQuery(callbackQueryId, text, showAlert, url, cacheTime).cast()
	}

	override fun restrictChatMember(chatId: String, userId: Long, permissions: String, untilDate: Int): BooleanResponse {
		return super.restrictChatMember(chatId, userId, permissions, untilDate).cast()
	}

	override fun restrictChatMember(chatId: Long, userId: Long, permissions: String, untilDate: Int): BooleanResponse {
		return super.restrictChatMember(chatId, userId, permissions, untilDate).cast()
	}


	private fun <T>Any?.cast() = this as T
}