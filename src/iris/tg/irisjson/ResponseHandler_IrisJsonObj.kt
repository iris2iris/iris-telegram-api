package iris.tg.irisjson

import iris.json.flow.JsonFlowParser
import iris.tg.api.ResponseHandler
import iris.tg.irisjson.response.*
import java.io.InputStream

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class ResponseHandler_IrisJsonObj : ResponseHandler<IrisJsonResponse> {
	override fun process(method: String, data: String?): IrisJsonResponse {
		data ?: throw NullPointerException("data is null")
		val json = JsonFlowParser.start(data)
		return when (method) {
			"getUpdates" -> IrisJsonGetUpdatesResponse(json)
			"sendMessage" -> IrisJsonSendMessageResponse(json)
			"editMessageText" -> IrisJsonSendMessageResponse(json)

			"getChatMember" -> IrisJsonGetChatMemberResponse(json)
			"getChatAdministrators" -> IrisJsonGetChatAdministratorsResponse(json)
			"getChatMemberCount" -> IrisJsonGetChatMemberCountResponse(json)
			"banChatMember" -> IrisJsonBanChatMemberResponse(json)
			"unbanChatMember" -> IrisJsonBooleanResponse(json)
			"getChat" -> IrisJsonGetChatResponse(json)
			"deleteMessage" -> IrisJsonBooleanResponse(json)
			"sendPhoto" -> IrisJsonSendMessageResponse(json)
			"getFile" -> IrisJsonGetFileResponse(json)
			"answerCallbackQuery" -> IrisJsonBooleanResponse(json)
			"restrictChatMember" -> IrisJsonBooleanResponse(json)
			else -> IrisJsonResponseDefault(json)
		}
	}

	override fun process(method: String, inputStream: InputStream): IrisJsonResponse {
		TODO("Not yet implemented")
	}
}