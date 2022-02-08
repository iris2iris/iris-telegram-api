package iris.tg.webhook

import java.net.InetSocketAddress
import java.net.URI

/**
 * @created 26.12.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgWebhookRequestHandler {

	fun handle(request: Request)

	interface Request {

		fun findHeader(key: String): String?

		val requestUri: URI

		val remoteAddress: InetSocketAddress

		fun writeResponse(response: String, code: Int = 200)

		fun body(): String
	}
}