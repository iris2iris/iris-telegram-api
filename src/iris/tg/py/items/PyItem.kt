package iris.tg.py.items

import iris.tg.api.TgApiObjFuture
import iris.tg.api.response.SendMessageResponse
import iris.tg.py.Bot
import java.util.concurrent.CompletableFuture

abstract class PyItem(val bot: Bot?) {

	fun answer(text: String): CompletableFuture<SendMessageResponse>? {
		return bot?.api?.sendMessage(fromChatId, text)
	}

	abstract val fromChatId: Long
}