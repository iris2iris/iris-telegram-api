package iris.tg

import iris.tg.api.TgApiObject
import iris.tg.api.items.*
import iris.tg.api.response.TgResponse
import iris.tg.longpoll.GetUpdateExceptionHandler
import iris.tg.longpoll.TgLongPoll
import iris.tg.processors.TgUpdateProcessor
import iris.tg.processors.pack.*
import iris.tg.processors.single.TgEventSingleHandler
import iris.tg.processors.single.TgUpdateProcessorSingle
import iris.tg.processors.single.Update2CustomSingleHandler

/**
 * @created 06.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class QueuedService<T>(
	private val service: TgService,
	private val updateProcessor: TgUpdateProcessor<T>,
	private val buffer: TgReadable<T>
) : TgService {

	private var working = true

	override fun run() {
		service.start()
		working = true
		val thisThread = thread ?: Thread.currentThread()
		val buffer = buffer
		val updateProcessor = updateProcessor
		while (!thisThread.isInterrupted && working) {
			val items = buffer.readAll()
			if (items.isEmpty()) continue
			updateProcessor.processUpdates(items)
		}
	}

	private var thread: Thread? = null

	override fun start() {
		stop()
		thread = Thread(this).also { it.start() }
	}

	override fun stop() {
		service.stop()
		working = false
		thread?.interrupt()
	}

	override fun join() {
		service.join()
	}
}

interface TgService : Runnable {
	fun start()
	fun stop()
	fun join()
}

class QueueLongPollServiceBuilder {

	var api: TgApiObject? = null
	var updateProcessor: TgUpdateProcessor<Update>? = null

	/*var token: String? = null
	var apiPath: String = "https://api.telegram.org"
	var connection: Connection<String, ByteArray?>? = null*/
	var exceptionHandler: GetUpdateExceptionHandler<TgResponse>? = null

	var buffer: TgReadWriteUpdateBuffer<Update>? = null


	fun build(): QueuedService<Update> {
		val updateProcessor = updateProcessor ?: throw IllegalArgumentException("updateProcessor is not set")
		val api = api ?: throw IllegalStateException("Api is not set")/*token?.let { tgApi(it, apiPath, connection) } ?: throw IllegalStateException("Nor api neither token is set")*/
		val buffer = buffer ?: TgReadWriteBufferDefault(1000)

		val service = TgLongPoll(api, UP(buffer), exceptionHandler)
		return QueuedService(service, updateProcessor, buffer)
	}

	/*fun processor(handler: TgEventSingleHandlerBasicTypes): TgUpdateProcessor<Update> {
		return TgUpdateProcessorSingle(handler)
	}

	fun processor(handler: TgEventPackHandlerBasicTypes): TgUpdateProcessor<Update> {
		return TgUpdateProcessorPack(handler)
	}*/

	fun <M : Message
			, IQ : InlineQuery
			, CIR : ChosenInlineResult
			, CQ : CallbackQuery
			, SQ : ShippingQuery
			, PCQ: PreCheckoutQuery
			, P: Poll
			, PA: PollAnswer
			, CMU: ChatMemberUpdated
			, CJR: ChatJoinRequest
			>processor(handler: TgEventSingleHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>): TgUpdateProcessor<Update> {
		return TgUpdateProcessorSingle(Update2CustomSingleHandler(handler))
	}

	fun <M : Message
			, IQ : InlineQuery
			, CIR : ChosenInlineResult
			, CQ : CallbackQuery
			, SQ : ShippingQuery
			, PCQ: PreCheckoutQuery
			, P: Poll
			, PA: PollAnswer
			, CMU: ChatMemberUpdated
			, CJR: ChatJoinRequest
		>processor(handler: TgEventPackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>): TgUpdateProcessor<Update> {
		return TgUpdateProcessorPack(Update2CustomPackHandler(handler))
	}

	private class UP(private val buffer: TgReadWriteUpdateBuffer<Update>) : TgUpdateProcessor<Update> {
		override fun processUpdates(updates: List<Update>) {
			buffer.write(updates)
		}

		override fun processUpdate(update: Update) {
			buffer.write(update)
		}
	}
}