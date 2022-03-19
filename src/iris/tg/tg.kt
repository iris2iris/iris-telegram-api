package iris.tg

import iris.json.flow.JsonFlowParser
import iris.tg.api.ResponseHandler
import iris.tg.api.TgApiObjFuture
import iris.tg.api.TgApiObject
import iris.tg.api.items.*
import iris.tg.api.response.TgResponse
import iris.connection.Connection
import iris.connection.ConnectionHttpClientDefault
import iris.connection.ConnectionHttpClientFuture
import iris.tg.irisjson.ResponseHandler_IrisJsonObj
import iris.tg.irisjson.TgApiFuture_IrisJson
import iris.tg.irisjson.TgApi_IrisJson
import iris.tg.irisjson.items.IrisJsonUpdate
import iris.tg.processors.TgUpdateProcessor
import iris.tg.processors.pack.TgEventPackHandlerBasicTypes
import iris.tg.processors.pack.TgUpdateProcessorPack
import iris.tg.processors.single.TgEventSingleHandlerBasicTypes
import iris.tg.processors.single.TgUpdateProcessorSingle
import java.io.InputStream
import java.net.http.HttpClient
import java.nio.charset.Charset
import java.time.Duration
import java.util.concurrent.CompletableFuture

/**
 * @created 03.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */

fun tgApi(token: String, apiPath: String? = Tg.defaultApiPath, connection: Connection<String, ByteArray?>? = Tg.defaultConnection): TgApiObject
	= TgApi_IrisJson(token, apiPath, connection)

fun tgApiFuture(token: String, apiPath: String? = Tg.defaultApiPath, connection: Connection<CompletableFuture<String>, CompletableFuture<ByteArray?>>? = Tg.defaultConnectionFuture): TgApiObjFuture
	= TgApiFuture_IrisJson(token, apiPath, connection)

fun updateProcessor(handler: TgEventSingleHandlerBasicTypes): TgUpdateProcessor<Update> {
	return TgUpdateProcessorSingle(handler)
}

fun updateProcessor(handler: TgEventPackHandlerBasicTypes): TgUpdateProcessor<Update> {
	return TgUpdateProcessorPack(handler)
}

/*fun extUpdateProcessor(handler: TgEventSingleHandler): TgUpdateProcessor<UpdateExt> {
	return TgUpdateExtToSimpleProcessor(TgUpdateProcessorSingle(handler))
}

fun extUpdateProcessor(handler: TgEventPackHandler): TgUpdateProcessor<UpdateExt> {
	return TgUpdateExtToSimpleProcessor(TgUpdateProcessorPack(handler))
}*/

fun responseHandler(): ResponseHandler<TgResponse> = ResponseHandler_IrisJsonObj()

fun webhookBodyHandler(): ResponseHandler<Update?> = object : ResponseHandler<Update?> {

	override fun process(method: String, data: String?): Update? {
		if (data == null) return null
		return IrisJsonUpdate(JsonFlowParser.start(data))
	}

	override fun process(method: String, inputStream: InputStream): Update? {
		return process(method, inputStream.reader(Charset.defaultCharset()).readText())
	}
}

object Tg {
	val defaultHttpClient = HttpClient.newBuilder()
		.connectTimeout(Duration.ofSeconds(5))
		.version(HttpClient.Version.HTTP_2)
		.build()

	var defaultConnection = ConnectionHttpClientDefault(defaultHttpClient)
	var defaultConnectionFuture = ConnectionHttpClientFuture(defaultHttpClient)
	var defaultApiPath = "https://api.telegram.org"
}