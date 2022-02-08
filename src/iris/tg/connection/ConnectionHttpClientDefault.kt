package iris.tg.connection

import iris.tg.api.Options
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 * @created 30.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class ConnectionHttpClientDefault(client: HttpClient) : ConnectionHttpClientAbstract<String, ByteArray?>(client) {
    override fun <R> customRequest(request: HttpRequest, responseHandler: HttpResponse.BodyHandler<*>): R {
        return client.send(request, responseHandler).body() as R
    }
}