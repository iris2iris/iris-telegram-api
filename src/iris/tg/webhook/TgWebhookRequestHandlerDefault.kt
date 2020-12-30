package iris.tg.webhook

import iris.json.JsonItem
import iris.json.flow.JsonFlowParser
import iris.tg.webhook.TgWebhookRequestHandler.Request
import java.util.logging.Logger

/**
 * @created 26.12.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
class TgWebhookRequestHandlerDefault(
	private val gbSource: GroupbotSource,
	private var eventConsumer: TgWebhookEventConsumer,
	private val addressTester: AddressTester? = AddressTesterDefault(),
	expireEventTime: Long = 25_000L,
	tgTimeVsLocalTimeDiff: Long = 0L
) : TgWebhookRequestHandler {

	private val expireEventTime = if (expireEventTime == 0L) 0L else expireEventTime - tgTimeVsLocalTimeDiff
	private var expired = 0
	private val exp = Any()

	companion object {
		var loggingExpired = true

		private val logger = Logger.getLogger("iris.tg")
	}

	private inline fun ok(request: Request) {
		request.writeResponse("ok", 200)
	}

	override fun handle(request: Request) {
		logger.finest {"Callback API event from " + request.remoteAddress }

		if (addressTester != null) {
			if (!addressTester.isGoodHost(request)) {
				logger.info { "Unknown host trying to send Callback API event: " + addressTester.getRealHost(request) }
				ok(request)
				return
			}
		}

		var groupbot = gbSource.getGroupbot(request)
		if (groupbot == null) {
			logger.info { "Groupbot not found. " + request.requestUri }
			ok(request)
			return
		}


		val body = request.body()

		try {
			if (body.isEmpty()) {
				logger.fine {"Body was empty" }
				ok(request)
				return
			}

			val event: JsonItem = JsonFlowParser.start(body)

			//val groupId = groupbot.id

			/*val type = event["type"].asString()
			if (type == "confirmation") {
				val res = groupbot.confirmation
				logger.finest {"Test confirmation. Group ID: $groupId" }
				request.writeResponse(res, 200)
				return
			}*/

			ok(request)
			val eventWriter = eventConsumer


			val obj = event
			val suitsTime = if (expireEventTime != 0L) {
				val testDate = obj["message"]["date"].asLongOrNull()
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
				eventWriter.send(event)

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