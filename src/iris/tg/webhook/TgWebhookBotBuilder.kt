package iris.tg.webhook

import com.sun.net.httpserver.HttpServer
import iris.tg.TgEventHandler
import iris.tg.TgUpdateProcessor
import iris.tg.TgUpdateProcessorDefault
import java.net.InetSocketAddress
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Главный класс для создания Webhook сервера. Создаёт сервер TgWebhookBot из переданных настроек, готовый к запуску
 *
 * @created 26.12.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */

@Suppress("MemberVisibilityCanBePrivate")
class TgWebhookBotBuilder {
	var server: TgWebhookRequestServer? = null
	var groupbotSource: GroupbotSource? = null
	var groupbot: GroupbotSource.Groupbot? = null
	var eventReadWriteBuffer: TgWebhookReadWriteBuffer? = null
	var updateProcessor: TgUpdateProcessor? = null
	var eventHandler: TgEventHandler? = null
	var requestHandler: TgWebhookRequestHandler? = null
	var path: String = "/callback"
	var port: Int = 80
	var requestsExecutor: Executor? = null
	var addressTester: AddressTester? = AddressTesterDefault()
	var expireEventTime: Long = 25_000L
	var tgTimeVsLocalTimeDiff: Long = 0L

	companion object {
		fun build(initializer: TgWebhookBotBuilder.() -> Unit): TgWebhookBot {
			return TgWebhookBotBuilder().apply(initializer).buildGroupCallback()
		}
	}

	fun buildGroupCallback() : TgWebhookBot {
		val updateProcessor = this.updateProcessor ?: TgUpdateProcessorDefault(this.eventHandler ?: throw IllegalStateException("Event processor is not set"))
		val server = server ?: initDefaultServer(port, requestsExecutor?: Executors.newFixedThreadPool(4))


		val buffer = eventReadWriteBuffer?: let {

			TgWebhookReadWriteBufferDefault(1000)
		}

		val requestHandler = this.requestHandler
		if (requestHandler != null) {
			server.setHandler(path, requestHandler)
		} else {
			server.setHandler(path, TgWebhookRequestHandlerDefault(
				groupbotSource ?: GroupSourceSimple(groupbot ?: throw IllegalStateException("Neither groupbot nor groupbotSource were not set"))
				, buffer
				, addressTester
				, expireEventTime, tgTimeVsLocalTimeDiff
			))
		}

		return TgWebhookBot(server, buffer, updateProcessor)
	}

	private fun initDefaultServer(port: Int, requestsExecutor: Executor): TgWebhookRequestServer {
		val server = HttpServer.create()
		server.bind(InetSocketAddress(port), 0)
		server.executor = requestsExecutor
		return TgWebhookRequestServerDefault(server)
	}
}