package iris.tg.irisjson

import iris.tg.api.TgApiObject
import iris.tg.api.items.Update
import iris.tg.api.response.TgResponse
import iris.tg.longpoll.GetUpdateExceptionHandler
import iris.tg.longpoll.TgLongPoll
import iris.tg.processors.TgUpdateProcessor
import iris.tg.processors.pack.TgEventPackHandler
import iris.tg.processors.pack.TgEventPackHandlerBasicTypes
import iris.tg.processors.pack.TgUpdateProcessorPack
import iris.tg.processors.single.TgEventSingleHandler
import iris.tg.processors.single.TgEventSingleHandlerBasicTypes
import iris.tg.processors.single.TgUpdateProcessorSingle

/**
 * @created 30.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TgLongPoll_IrisJson(api: TgApiObject, updateProcessor: TgUpdateProcessor<Update>, exceptionHandler: GetUpdateExceptionHandler<TgResponse>? = null
) : TgLongPoll(api, updateProcessor, exceptionHandler) {

	constructor(token: String, handler: TgEventSingleHandlerBasicTypes, exceptionHandler: GetUpdateExceptionHandler<TgResponse>? = null)
		: this(TgApi_IrisJson(token), TgUpdateProcessorSingle(handler), exceptionHandler)

	constructor(token: String, handler: TgEventPackHandlerBasicTypes, exceptionHandler: GetUpdateExceptionHandler<TgResponse>? = null)
		: this(TgApi_IrisJson(token), TgUpdateProcessorPack(handler), exceptionHandler)
}