package iris.tg.api

import iris.tg.connection.Connection
import iris.tg.connection.ConnectionHttpClientFuture
import iris.tg.connection.query.Query
import java.net.http.HttpClient
import java.util.concurrent.CompletableFuture

/**
 * @created 25.01.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TgApiFuture<T>(token: String,
					 private val responseHandler: ResponseHandler<T>,
					 apiPath: String? = null,
					 connection: Connection<CompletableFuture<String>, CompletableFuture<ByteArray?>>? = null
) : TgApiAbstract<CompletableFuture<out T>>(token, apiPath) {

	private val connection = connection ?: ConnectionHttpClientFuture(HttpClient.newHttpClient())

	override fun requestImpl(url: String, method: String, options: Query?): CompletableFuture<T> {
		return connection.request(url, options)
			.thenApply {responseHandler.process(method, it) }
	}

	override fun requestUploadImpl(url: String, method: String, files: Map<String, Connection.BinaryData>, options: Query?): CompletableFuture<T> {
		return connection.requestUpload(url, files, options)
			.thenApply {responseHandler.process(method, it) }
	}
}