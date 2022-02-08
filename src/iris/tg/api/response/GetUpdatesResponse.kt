package iris.tg.api.response

import iris.tg.api.items.Update

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface GetUpdatesResponse: TgResponse {
	override val result : List<Update>?
}