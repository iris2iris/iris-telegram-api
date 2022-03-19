package iris.connection

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 * @created 30.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class ConnectionHttpClientDefault(client: HttpClient, timeout: Long = 0) : ConnectionHttpClientAbstract<String, ByteArray?>(client, timeout) {
    override fun <R> customRequest(request: HttpRequest, responseHandler: HttpResponse.BodyHandler<*>): R {
        return client.send(request, responseHandler).body() as R
    }
}