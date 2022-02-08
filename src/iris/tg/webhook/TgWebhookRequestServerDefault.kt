package iris.tg.webhook

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.net.URI

/**
 * Обёртка для HttpServer и обработки входящих запросов
 */
class TgWebhookRequestServerDefault(private val server: HttpServer): HttpHandler, TgWebhookRequestServer {

	private class DefaultRequest(private val request: HttpExchange) : TgWebhookRequestHandler.Request {

		override val requestUri: URI = request.requestURI

		override val remoteAddress: InetSocketAddress = request.remoteAddress

		override fun findHeader(key: String): String? {
			return request.requestHeaders.getFirst(key)
		}

		override fun writeResponse(response: String, code: Int) {
			_writeResponse(response, code)
		}

		override fun body(): String {
			return request.requestBody.reader().use { it.readText() }
		}

		private fun _writeResponse(str: String, rCode: Int = 200) {
			val bytes = str.toByteArray()
			val request = this.request
			request.sendResponseHeaders(rCode, bytes.size.toLong())
			request.responseBody.use { it.write(bytes) }
			request.close()
		}
	}

	private lateinit var handler: TgWebhookRequestHandler

	override fun setHandler(path: String, handler: TgWebhookRequestHandler) {
		this.handler = handler
		server.createContext(path, this)
	}

	override fun handle(exchange: HttpExchange) {
		handler.handle(DefaultRequest(exchange))
	}

	override fun start() {
		try {
			server.start()
		} catch (e: IllegalStateException) {
			e.printStackTrace()
		}
	}

	override fun stop(seconds: Int) {
		server.stop(seconds)
	}

	override fun stop() {
		stop(1)
	}

	override fun join() {
		TODO("Not yet implemented")
	}

	override fun run() = start()
}