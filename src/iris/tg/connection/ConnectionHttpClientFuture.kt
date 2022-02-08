package iris.tg.connection

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

/**
 * @created 30.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class ConnectionHttpClientFuture(client: HttpClient) : ConnectionHttpClientAbstract<CompletableFuture<String>, CompletableFuture<ByteArray?>>(client) {

	override fun <R> customRequest(request: HttpRequest, responseHandler: HttpResponse.BodyHandler<*>): R {
		return client.sendAsync(request, responseHandler).thenApply { it.body() } as R
	}
}