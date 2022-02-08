package iris.tg.webhook

import iris.tg.TgUpdateWriter
import iris.tg.api.ResponseHandler
import iris.tg.api.items.Update
import iris.tg.api.items.UpdateExt
import iris.tg.processors.TgUpdateMultibotProcessor
import iris.tg.processors.TgUpdateMultibotProcessorToUpdateProcessor
import iris.tg.processors.TgUpdateProcessor
import iris.tg.webhook.TgWebhookRequestHandler.Request
import java.util.logging.Logger

/**
 * @created 26.12.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TgWebhookRequestHandlerDefault(
	private val updateProcessor: TgUpdateMultibotProcessor<Update>,
	private val responseHandler: ResponseHandler<Update?>,
	private val addressTester: AddressTester? = AddressTesterDefault(),
	private val botSource: BotSource? = null,
	expireEventTime: Long = 25_000L,
	tgTimeVsLocalTimeDiff: Long = 0L,
) : TgWebhookRequestHandler {


	constructor(
		updateWriter: TgUpdateWriter<UpdateExt>,
		responseHandler: ResponseHandler<Update?>,
		addressTester: AddressTester? = AddressTesterDefault(),
		botSource: BotSource? = null,
		expireEventTime: Long = 25_000L,
		tgTimeVsLocalTimeDiff: Long = 0L,
	) : this(Writer2MultibotProcessor(updateWriter), responseHandler, addressTester, botSource, expireEventTime, tgTimeVsLocalTimeDiff)

	constructor(
		updateProcessor: TgUpdateProcessor<Update>,
		responseHandler: ResponseHandler<Update?>,
		addressTester: AddressTester? = AddressTesterDefault(),
		botSource: BotSource? = null,
		expireEventTime: Long = 25_000L,
		tgTimeVsLocalTimeDiff: Long = 0L,
	) : this(TgUpdateMultibotProcessorToUpdateProcessor(updateProcessor), responseHandler, addressTester, botSource, expireEventTime, tgTimeVsLocalTimeDiff)

	private class Writer2MultibotProcessor(private val updateWriter: TgUpdateWriter<UpdateExt>) :
		TgUpdateMultibotProcessor<Update> {
		override fun processUpdates(fromBot: BotSource.BotData, updates: List<Update>) {
			TODO("Not yet implemented")
		}

		override fun processUpdate(fromBot: BotSource.BotData, update: Update) {
			updateWriter.write(UpdateExt(update, fromBot))
		}
	}

	private val expireEventTime = if (expireEventTime == 0L) 0L else expireEventTime - tgTimeVsLocalTimeDiff
	private var expired = 0
	private val exp = Any()

	companion object {
		var loggingExpired = true

		private val logger = Logger.getLogger("iris.tg")
	}

	private inline fun ok(request: Request) {
		request.writeResponse("", 200)
	}

	override fun handle(request: Request) {
		logger.finest {"Callback API event from " + request.remoteAddress }

		if (addressTester != null) {
			if (!addressTester.isGoodHost(request)) {
				logger.info { "Unknown host trying to send Webhook event: " + addressTester.getRealHost(request) }
				ok(request)
				return
			}
		}

		val bot = if (botSource != null) {
			val bot = botSource.getBot(request)
			if (bot == null) {
				logger.info { "BotData not found. " + request.requestUri }
				ok(request)
				return
			}
			bot
		} else
			null

		val body = request.body()

		try {
			if (body.isEmpty()) {
				logger.fine {"Body was empty" }
				ok(request)
				return
			}

			val event = responseHandler.process("getUpdates", body)
			ok(request)
			if (event == null) return

			val suitsTime = if (expireEventTime != 0L) {
				val testDate = event.message?.date
				if (testDate != null) {
					val date = testDate
					val curTime = System.currentTimeMillis()
					date * 1000 > curTime - expireEventTime
				} else
					true
			} else
				true

			if (suitsTime) {
				// отправляем событие
				updateProcessor.processUpdate(bot ?: BotSource.BotData.zero, event)

				if (loggingExpired) {
					synchronized(exp) {
						expired = 0
					}
				}
			} else {
				if (loggingExpired) {
					synchronized(exp) {
						expired++
					}
					if (expired % 50 == 1)
						logger.info { "Expired $expired" }
				}
			}
		} catch (e: Exception) {
			logger.severe { e.stackTraceToString() }
			ok(request)
		}
	}
}