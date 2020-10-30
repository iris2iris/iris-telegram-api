package iris.connection

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

/**
 * @created 30.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
class ConnectionHttpClientDefault(client: HttpClient) : ConnectionHttpClientAbstract<HttpResponse<String>, HttpResponse<ByteArray>>(client) {
    override fun <R> customRequest(request: HttpRequest, responseHandler: HttpResponse.BodyHandler<*>): R {
        return client.send(request, responseHandler) as R
    }
}