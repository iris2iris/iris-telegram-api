package iris.tg.py.items

import iris.tg.api.TgApiObjFuture
import iris.tg.api.response.SendMessageResponse
import java.util.concurrent.CompletableFuture

abstract class PyItem(val api: TgApiObjFuture) {

	fun answer(text: String): CompletableFuture<SendMessageResponse> {
		return api.sendMessage(fromChatId, text)
	}

	abstract val fromChatId: Long
}