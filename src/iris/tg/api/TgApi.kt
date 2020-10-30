package iris.tg.api

import iris.connection.Connection
import iris.connection.ConnectionHttpClientDefault
import iris.json.JsonItem
import iris.json.flow.JsonFlowParser
import iris.util.Options
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.concurrent.CompletableFuture

/**
 * @created 30.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */

class TgApi(token: String, connection: Connection<HttpResponse<String>, HttpResponse<ByteArray>>? = null) : Methods<JsonItem?>(token) {

	private val connection = connection?: kotlin.run {
		ConnectionHttpClientDefault(HttpClient.newBuilder()
			.connectTimeout(Duration.ofSeconds(15))
			.build())
	}

	companion object {
		private val emptyItem = CompletableFuture.completedFuture<JsonItem?>(null)
	}

	private val urlCache = HashMap<String, String>()

	override fun request(method: String, options: Options?, token: String?): JsonItem? {
		val sb = StringBuilder()
		if (options != null)
			encodeOptions(options, sb)
		val url = if (token == null) (urlCache.getOrPut(method) { "https://api.telegram.org/bot${this.token}/$method?" }) + sb
		else "https://api.telegram.org/bot$token/$method?$sb"

		return parse(connection.request(url).body())
	}

	fun parse(response: String): JsonItem {
		return JsonFlowParser.start(response)
	}

	override fun requestUpload(method: String, options: Options?, data: Map<String, Options>, token: String?): JsonItem? {
		val sb = StringBuilder()
		if (options != null)
			encodeOptions(options, sb)
		val url = if (token == null) (urlCache.getOrPut(method) { "https://api.telegram.org/bot${this.token}/$method?" }) + sb
		else "https://api.telegram.org/bot$token/$method?$sb"

		return parse(connection.requestUpload(url, data).body())
	}

	private fun encode(o: String): String? {
		return URLEncoder.encode(o, StandardCharsets.UTF_8)
	}

	private fun encodeOptions(obj: Options, sb: StringBuilder = StringBuilder()): StringBuilder {
		for (o in obj.entries) {
			sb.append(encode(o.key)).append('=')
				.append(encode(o.value.toString())).append("&")
		}
		return sb
	}


	fun getFileBinary(fileId: String): ByteArray? {
		val res = getFile(fileId)?: return null
		val path = getPath(res["result"]["file_path"].asString())
		return connection.requestByteArray(path).body()
	}
}

