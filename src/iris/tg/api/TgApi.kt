package iris.tg.api

import iris.tg.connection.Connection
import iris.tg.connection.ConnectionHttpClientDefault
import java.net.http.HttpClient

/**
 * @created 25.01.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TgApi<T>(token: String,
	   private val responseHandler: ResponseHandler<T>,
	   apiPath: String? = null,
	   connection: Connection<String, ByteArray?>? = null
) : TgApiAbstract<T>(token, apiPath) {

	private val connection = connection ?: ConnectionHttpClientDefault(HttpClient.newHttpClient())

	override fun requestImpl(url: String, method: String, options: Options?): T {
		return connection.request(url, options)
			.let { responseHandler.process(method, it) }
	}

	override fun requestUploadImpl(url: String, method: String, files: Map<String, Connection.BinaryData>, options: Options?): T {
		return connection.requestUpload(url, files, options)
			.let { responseHandler.process(method, it) }
	}
}