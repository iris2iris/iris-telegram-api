package iris.tg.webhook

import iris.json.JsonItem

interface TgWebhookEventConsumer {
	fun send(event: JsonItem)
}