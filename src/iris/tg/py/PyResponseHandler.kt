package iris.tg.py

import iris.json.flow.JsonFlowParser
import iris.tg.api.TgApiObjFuture
import iris.tg.irisjson.ResponseHandler_IrisJsonObj
import iris.tg.irisjson.response.*
import iris.tg.py.response.PyGetUpdateResponse
import iris.tg.py.response.PySendMessageResponse
import java.io.InputStream

open class PyResponseHandler(private val bot: Bot? = null) : ResponseHandler_IrisJsonObj() {

	override fun process(method: String, data: String?): IrisJsonResponse {
		data ?: throw NullPointerException("data is null")
		val json = JsonFlowParser.start(data)
		return when (method) {
			"getUpdates" -> PyGetUpdateResponse(bot, json)
			"sendMessage" -> PySendMessageResponse(json)
			"editMessageText" -> PySendMessageResponse(json)

			"getChatMember" -> IrisJsonGetChatMemberResponse(json)
			"getChatAdministrators" -> IrisJsonGetChatAdministratorsResponse(json)
			"getChatMemberCount" -> IrisJsonGetChatMemberCountResponse(json)
			"banChatMember" -> IrisJsonBanChatMemberResponse(json)
			"unbanChatMember" -> IrisJsonBooleanResponse(json)
			"getChat" -> IrisJsonGetChatResponse(json)
			"deleteMessage" -> IrisJsonBooleanResponse(json)
			"sendPhoto" -> PySendMessageResponse(json)
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