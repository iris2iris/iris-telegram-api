package iris.tg.api.response

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface IntegerResponse : TgResponse {
	override val result: Int
}