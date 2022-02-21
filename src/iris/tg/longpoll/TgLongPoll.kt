package iris.tg.longpoll

import iris.tg.QueueLongPollServiceBuilder
import iris.tg.QueuedService
import iris.tg.TgService
import iris.tg.api.TgApiObject
import iris.tg.api.items.Update
import iris.tg.api.response.GetUpdatesResponse
import iris.tg.api.response.TgResponse
import iris.tg.processors.TgUpdateMultibotProcessor
import iris.tg.processors.TgUpdateProcessor
import iris.tg.processors.TgUpdateProcessor2Multibot
import iris.tg.processors.pack.TgEventPackHandlerBasicTypes
import iris.tg.processors.pack.TgUpdateProcessorPack
import iris.tg.processors.single.TgEventSingleHandlerBasicTypes
import iris.tg.processors.single.TgUpdateProcessorSingle
import iris.tg.webhook.BotSource
import java.util.logging.Logger

/**
 * @created 30.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TgLongPoll(private val api: TgApiObject, private val updateProcessor: TgUpdateProcessor<Update>, exceptionHandler: GetUpdateExceptionHandler<TgResponse>? = null) : TgService {

	constructor(api: TgApiObject, handler: TgEventSingleHandlerBasicTypes, exceptionHandler: GetUpdateExceptionHandler<TgResponse>? = null)
			: this(api, TgUpdateProcessorSingle(handler), exceptionHandler)

	constructor(api: TgApiObject, handler: TgEventPackHandlerBasicTypes, exceptionHandler: GetUpdateExceptionHandler<TgResponse>? = null)
			: this(api, TgUpdateProcessorPack(handler), exceptionHandler)

	constructor(api: TgApiObject, botData: BotSource.BotData, handler: TgUpdateMultibotProcessor<Update>, exceptionHandler: GetUpdateExceptionHandler<TgResponse>? = null)
			: this(api, TgUpdateProcessor2Multibot<Update>(botData, handler), exceptionHandler)

	open class GetUpdateExceptionHandlerDefault(/*private val sleepOnNull: Long = 100L, */private val sleepOnNotOk: Long = 100L) : GetUpdateExceptionHandler<TgResponse> {

		override fun handle(e: Throwable): Boolean = throw e

		/*override fun nullUpdates(): Boolean {
			logger.warning("NOT OK : Updates are null")
			if (sleepOnNull > 0L)
				Thread.sleep(sleepOnNull)
			return true
		}*/

		override fun notOk(errorItem: TgResponse): Boolean {
			val error = errorItem.error ?: return true
			logger.warning { with (error) { "NOT OK: $description ($errorCode)" } }
			if (sleepOnNotOk > 0L)
				Thread.sleep(sleepOnNotOk)
			return true
		}
	}

	companion object {

		fun queued(initializer: QueueLongPollServiceBuilder.() -> Unit) = longPollQueued(initializer)

		private val logger = Logger.getLogger("iris.tg")
	}

	private val exceptionHandler = exceptionHandler ?: GetUpdateExceptionHandlerDefault()

	var isWorking = true
	var updatesLimit = 100
	var waitSeconds = 10
	var offset = -1L

	override fun run() {
		isWorking = true

		val thisThread = Thread.currentThread()
		val exceptionHandler = exceptionHandler
		val updateProcessor = updateProcessor
		while (!thisThread.isInterrupted && isWorking)  {
			try {
				val updates = api.getUpdates(offset, updatesLimit, waitSeconds)
				/*if (updates == null) {
					if (!exceptionHandler.nullUpdates())
						stop()
					continue
				}*/

				if (!updates.ok) {
					if (!exceptionHandler.notOk(updates))
						stop()
					continue
				}

				val items = updates.result
				if (items.isNullOrEmpty())
					continue
				offset = items.last().updateId + 1
				updateProcessor.processUpdates(items)
			} catch (e: Throwable) {
				exceptionHandler.handle(e)
			}
		}
	}

	private var thread: Thread? = null

	open fun startPolling() {
		thread?.interrupt()
		thread = Thread(this).also { it.start() }
	}

	override fun start() = startPolling()

	override fun join() {
		thread?.join()
	}

	override fun stop() {
		isWorking = false
		thread?.interrupt()
	}

}

fun longPollQueued(initializer: QueueLongPollServiceBuilder.() -> Unit): QueuedService<Update> {
	return QueueLongPollServiceBuilder().apply(initializer).build()
}
