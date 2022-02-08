package iris.tg.webhook

import iris.tg.TgService

/**
 * Можно взаимодействовать с любой реализацией сервера входящих запросов через данный интерфейс
 * в метод `setHandler` передаётся обработчик запросов по указанному URI
 * Данный сервер должен вызывать метод `TgWebhookRequestHandler.handle(request: Request)` каждый раз, как получает входящий запрос
 * @see TgWebhookRequestServerDefault — базовая реализация сервера входящих запросов
 */
interface TgWebhookRequestServer : TgService {

	fun setHandler(path: String, handler: TgWebhookRequestHandler)
	fun stop(seconds: Int)

}