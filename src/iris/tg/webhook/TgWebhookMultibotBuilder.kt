package iris.tg.webhook

import com.sun.net.httpserver.HttpServer
import com.sun.net.httpserver.HttpsConfigurator
import com.sun.net.httpserver.HttpsParameters
import com.sun.net.httpserver.HttpsServer
import iris.tg.QueuedService
import iris.tg.TgReadWriteBufferDefault
import iris.tg.TgReadWriteUpdateBuffer
import iris.tg.api.ResponseHandler
import iris.tg.api.items.Update
import iris.tg.api.items.UpdateExt
import iris.tg.processors.TgUpdateProcessor
import iris.tg.webhookBodyHandler
import java.io.FileInputStream
import java.net.InetSocketAddress
import java.security.KeyStore
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

/**
 * Главный класс для создания Webhook сервера. Создаёт сервер TgWebhookBot из переданных настроек, готовый к запуску
 *
 * @created 26.12.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */

@Suppress("MemberVisibilityCanBePrivate")
class TgWebhookMultibotBuilder {

	var server: TgWebhookRequestServer? = null
	var botSource: BotSource? = null
	var botData: BotSource.BotData? = null
	var updateReadWriteBuffer: TgReadWriteUpdateBuffer<UpdateExt>? = null
	var updateProcessor: TgUpdateProcessor<UpdateExt>? = null
	var requestHandler: TgWebhookRequestHandler? = null
	var path: String = "/webhook"
	var port: Int = 80
	var requestsExecutor: Executor? = null
	var addressTester: AddressTester? = AddressTesterDefault()
	var expireEventTime: Long = 25_000L
	var tgVsLocalTimeDiff: Long = 0L
	var responseHandler: ResponseHandler<Update?>? = null

	companion object {

		fun httpsServer(password: CharArray, keystorePath: String, requestsExecutor: Executor = Executors.newFixedThreadPool(4), port: Int = 443): TgWebhookRequestServer {
			val server = HttpsServer.create(InetSocketAddress(port), 0)
			server.executor = requestsExecutor

			val sslContext = SSLContext.getInstance("TLS")

			val password = password
			val ks = KeyStore.getInstance("JKS")
			FileInputStream(keystorePath).use {
				ks.load(it, password)
			}

			val kmf = KeyManagerFactory.getInstance("SunX509")
			kmf.init(ks, password)
			val tmf = TrustManagerFactory.getInstance("SunX509")
			tmf.init(ks)

			sslContext.init(kmf.keyManagers, tmf.trustManagers, null)
			server.httpsConfigurator = object : HttpsConfigurator(sslContext) {
				override fun configure(params: HttpsParameters) {
					val c = SSLContext.getDefault()
					val engine = c.createSSLEngine()
					params.needClientAuth = false
					params.cipherSuites = engine.enabledCipherSuites
					params.protocols = engine.enabledProtocols

					val defaultSSLParameters = c.defaultSSLParameters
					params.setSSLParameters(defaultSSLParameters)
				}
			}

			return TgWebhookRequestServerDefault(server)
		}
	}

	fun build() : QueuedService<UpdateExt> {
		val updateProcessor = this.updateProcessor ?: throw IllegalArgumentException("Event processor is not set")
		val botSource = botSource ?: throw IllegalArgumentException("Bot source is not set")
		val buffer = updateReadWriteBuffer?: TgReadWriteBufferDefault(1000)
		val server = server ?: initDefaultServer(port, requestsExecutor?: Executors.newFixedThreadPool(4))
		val requestHandler = this.requestHandler

		if (requestHandler != null) {
			server.setHandler(path, requestHandler)
		} else {
			val responseHandler = responseHandler ?: webhookBodyHandler()
			server.setHandler(path, TgWebhookRequestHandlerDefault(
				buffer
				, responseHandler
				, addressTester
				, botSource
				, expireEventTime, tgVsLocalTimeDiff
			))
		}

		return QueuedService(server, updateProcessor, buffer)
	}

	private fun initDefaultServer(port: Int, requestsExecutor: Executor): TgWebhookRequestServer {
		val server = HttpServer.create(InetSocketAddress(port), 0)
		server.executor = requestsExecutor
		return TgWebhookRequestServerDefault(server)
	}


}

fun webhookMultibot(initializer: TgWebhookMultibotBuilder.() -> Unit): QueuedService<UpdateExt> {
	return TgWebhookMultibotBuilder().apply(initializer).build()
}