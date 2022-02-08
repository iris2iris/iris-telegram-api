package iris.tg.api.response

import iris.tg.api.items.Error

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgResponse {
	val ok: Boolean
	val error: Error?
	val result: Any?
}