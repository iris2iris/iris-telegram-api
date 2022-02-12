package iris.tg.webhook

import com.sun.net.httpserver.HttpServer
import iris.tg.*
import iris.tg.api.ResponseHandler
import iris.tg.api.items.Update
import iris.tg.processors.TgUpdateMultibotProcessor
import iris.tg.processors.TgUpdateProcessor
import iris.tg.processors.pack.TgEventPackHandlerBasicTypes
import iris.tg.processors.pack.TgUpdateProcessorPack
import iris.tg.processors.single.TgEventMessageSingleHandlerBasicTypes
import iris.tg.processors.single.TgUpdateProcessorSingle
import java.net.InetSocketAddress
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class TgWebhookBotBuilder {
	var server: TgWebhookRequestServer? = null
	var updateReadWriteBuffer: TgReadWriteUpdateBuffer<Update>? = null
	var updateProcessor: TgUpdateProcessor<Update>? = null
	var requestHandler: TgWebhookRequestHandler? = null
	var path: String = "/webhook"
	var port: Int = 80
	var requestsExecutor: Executor? = null
	var addressTester: AddressTester? = AddressTesterDefault()
	var expireEventTime: Long = 0L
	var tgTimeVsLocalTimeDiff: Long = 0L
	var responseHandler: ResponseHandler<Update?>? = null

	fun build() : QueuedService<Update> {
		val buffer = updateReadWriteBuffer?: TgReadWriteBufferDefault(1000)
		val server = server ?: initDefaultServer(port, requestsExecutor?: Executors.newFixedThreadPool(4))
		val updateProcessor = this.updateProcessor ?: throw IllegalStateException("Event processor is not set")
		val requestHandler = this.requestHandler

		if (requestHandler != null) {
			server.setHandler(path, requestHandler)
		} else {
			val responseHandler = responseHandler ?: webhookBodyHandler()
			server.setHandler(path, TgWebhookRequestHandlerDefault(
				object : TgUpdateMultibotProcessor<Update> {
					override fun processUpdates(fromBot: BotSource.BotData, updates: List<Update>) {
						buffer.write(updates)
					}

					override fun processUpdate(fromBot: BotSource.BotData, update: Update) {
						buffer.write(update)
					}
				}, responseHandler, addressTester, null, expireEventTime, tgTimeVsLocalTimeDiff
			))
		}

		return QueuedService(server, updateProcessor, buffer)
	}

	private fun initDefaultServer(port: Int, requestsExecutor: Executor): TgWebhookRequestServer {
		val server = HttpServer.create(InetSocketAddress(port), 0)
		server.executor = requestsExecutor
		return TgWebhookRequestServerDefault(server)
	}

	fun toProcessor(handler: TgEventMessageSingleHandlerBasicTypes): TgUpdateProcessor<Update> {
		return TgUpdateProcessorSingle(handler)
	}

	fun toProcessor(handler: TgEventPackHandlerBasicTypes): TgUpdateProcessor<Update> {
		return TgUpdateProcessorPack(handler)
	}
}

fun webhookBot(initializer: TgWebhookBotBuilder.() -> Unit): QueuedService<Update> {
	return TgWebhookBotBuilder().apply(initializer).build()
}