package iris.tg.api

import iris.connection.Connection
import iris.json.JsonItem
import iris.util.Options
import java.util.concurrent.CompletableFuture

/**
 * @created 30.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
abstract class Methods<SingleType>(protected val token: String) {
    
    abstract fun request(method: String, options: Options?, token: String?): SingleType

	abstract fun requestUpload(method: String, options: Options?, data: Map<String, Options>, token: String?): SingleType
    
    fun sendMessage(chatId: Long, text: String, options: Options? = null, token: String? = null): SingleType {
        val options = options?: Options()
        options["chat_id"] = chatId
        options["text"] = text
        return request("sendMessage", options, token)
    }

    fun editMessageText(chatId: Long, messageId: Int, text: String, options: Options? = null, token: String? = null): SingleType {
        val options = options?: Options()
        options["chat_id"] = chatId
        options["message_id"] = messageId
        options["text"] = text
        return request("editMessageText", options, token)
    }

    fun getUpdates(offset: Long = 0, timeout: Int = 10, allowedTypes: String? = null): SingleType {
        val options = Options()
        if (offset != 0L)
            options["offset"] = offset
        if (timeout != 0)
            options["timeout"] = timeout
        if (allowedTypes != null)
            options["allowed_updates"] = allowedTypes
        return request("getUpdates", options, null)
    }

    fun chatsGetMember(chatId: Long, userId: Int, token: String? = null): SingleType {
        return request("getChatMember", Options("chat_id" to chatId, "user_id" to userId), token)
    }

    fun chatsGetAdministrators(chatId: Long, token: String? = null): SingleType {
        return request("getChatAdministrators", Options("chat_id" to chatId), token)
    }

    fun chatsGetMembersAmount(chatId: Long, token: String? = null): SingleType {
        return request("getChatMembersCount", Options("chat_id" to chatId), token)
    }

    fun chatsKickMember(chatId: Long, userId: Long, untilDate: Int = 0, token: String? = null): SingleType {
        val options = Options("chat_id" to chatId, "user_id" to userId)
        if (untilDate != 0)
            options["until_date"] = untilDate
        return request("kickChatMember", options, token)
    }

    fun chatUnbanMember(chatId: Long, userId: Long): SingleType {
        return request("unbanChatMember", Options("chat_id" to chatId, "user_id" to userId), null)
    }

    fun chatsGet(chatId: Long, token: String? = null): SingleType {
        return request("getChat", Options("chat_id" to chatId), token)
    }

    fun chatsGet(chatId: String): SingleType {
        return request("getChat", Options("chat_id" to chatId), null)
    }

    fun deleteMessage(chatId: Long, localId: Int, token: String? = null): SingleType {
        return request("deleteMessage", Options("chat_id" to chatId, "message_id" to localId), token)
    }

    fun chatsUnbanMember(chatId: Long, userId: Int, token: String? = null): SingleType {
        return request("unbanChatMember", Options("chat_id" to chatId, "user_id" to userId), token)
    }

    fun sendPhoto(chatId: Long, file: ByteArray, type: String, options: Options? = null, token: String? = null): SingleType {
        val options = options?: Options()
        options["chat_id"] = chatId
        return requestUpload("sendPhoto", options, mapOf("photo" to Options("data" to file, "Content-Type" to "image/$type")), token)
    }

	fun sendPhoto(chatId: Long, fileId: String, options: Options? = null, token: String? = null): SingleType {
        val options = options?: Options()
        options["chat_id"] = chatId
        options["photo"] = fileId
        return request("sendPhoto", options, token)
    }

    fun getChat(chatId: Long): SingleType {
        return request("getChat", Options("chat_id" to chatId), null)
    }

    fun getFile(fileId: String): SingleType {
        return request("getFile", Options("file_id" to fileId), null)
    }

    fun getPath(filePath: String): String {
        return "https://api.telegram.org/file/bot$token/$filePath"
    }

    fun answerCallbackQuery(callback_query_id: String, text: String? = null, showAlert: Boolean = false, url: String? = null, cache_time: Int = 0, token: String? = null): SingleType {
        val options = Options("callback_query_id" to callback_query_id)
        if (text != null) options["text"] = text
        if (showAlert) options["show_alert"] = showAlert
        if (url != null) options["url"] = url
        if (cache_time != 0) options["cache_time"] = cache_time
        return request("answerCallbackQuery", options, token)
    }

    fun restrictChatMember(chatId: Long, userId: Long, permissions: Options, untilDate: Int = 0, token: String? = null): SingleType {
        permissions["chat_id"] = chatId
        permissions["user_id"] = userId
        permissions["until_date"] = untilDate
        return request("restrictChatMember", permissions, token)
    }
}