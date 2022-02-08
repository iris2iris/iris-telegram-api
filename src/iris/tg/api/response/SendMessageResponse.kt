package iris.tg.api.response

import iris.tg.api.items.Message

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface SendMessageResponse : TgResponse {
	override val result: Message?
}