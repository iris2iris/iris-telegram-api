package iris.tg.api.response

import iris.tg.api.items.Chat

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface GetChatResponse : TgResponse {
	override val result: Chat?
}