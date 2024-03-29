package iris.connection

import iris.util.Options
import java.io.File
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration

/**
 * @created 25.10.2019
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
abstract class ConnectionHttpClientAbstract<Response, BinaryResponse>(protected val client: HttpClient) : Connection<Response, BinaryResponse> {

	override fun request(url: String, data: String?): Response {
		return request(url, if (data == null) null else HttpRequest.BodyPublishers.ofString(data), HttpResponse.BodyHandlers.ofString())
	}

	override fun request(url: String, data: Map<String, Any>?): Response {
		return request(url, encodeOptions(data))
	}

	fun request(url: String, data: HttpRequest.BodyPublisher?, responseHandler: HttpResponse.BodyHandler<*>, headers: Map<String, String>? = null): Response {
		var builder = HttpRequest.newBuilder()
				.uri(URI.create(url))
		if (data != null)
			builder = builder.POST(data)
		headers?.forEach { t, u ->
			builder.header(t, u)
		}

		val request = builder.build()

		return customRequest(request, responseHandler)
	}

	protected abstract fun <T>customRequest(request: HttpRequest, responseHandler: HttpResponse.BodyHandler<*>): T

	override fun requestUpload(url: String, files: Map<String, Options>, data: Map<String, Any>?): Response {
		val map = data?.toMutableMap() ?: mutableMapOf()
		map.putAll(files)

		val boundary = "testing"
		val request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.POST(ofMimeMultipartData(map, boundary))
				.header("Content-Type", "multipart/form-data; boundary=\"$boundary\"")
				.build()

		return customRequest(request, HttpResponse.BodyHandlers.ofString())
	}

	override fun requestByteArray(url: String): BinaryResponse {
		val request = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.build()
		return customRequest(request, HttpResponse.BodyHandlers.ofByteArray())
	}

	private fun encode(o: String): String? {
		return URLEncoder.encode(o, StandardCharsets.UTF_8)
	}

	private fun encodeOptions(obj: Map<String, Any?>?): String? {
		val sb = StringBuilder()
		if (obj != null)
			for (o in obj.entries) {
				sb.append(encode(o.key)).append('=')
						.append(encode(o.value.toString())).append("&")
			}
		return sb.toString()
	}

	private fun ofMimeMultipartData(dataItem: Map<String, Any>, boundary: String): HttpRequest.BodyPublisher {
		val byteArrays = ArrayList<ByteArray>()
		val separator = ("--$boundary\r\nContent-Disposition: form-data; name=").toByteArray()

		for (entry in dataItem.entries) {
			byteArrays.add(separator)
			val value = entry.value
			if (value is Options) {
				when {
					value["file"] != null -> {
						val path = if (value["file"] is File) Path.of((value["file"] as File).toURI()) else Path.of(value["file"] as String)
						val mimeType = if (value["Content-Type"] != null) value.getString("Content-Type") else Files.probeContentType(path)
						val filename = value["filename"] ?: path.fileName
						byteArrays.add(("\"" + encode(entry.key) + "\"; filename=\"" + filename + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").toByteArray())
						byteArrays.add(Files.readAllBytes(path))
						byteArrays.add("\r\n".toByteArray())
					}
					value["data"] is ByteArray -> {
						val mimeType = if (value["Content-Type"] != null) value.getString("Content-Type") else "application/octet-stream"
						val filename = value.getStringOrNull("filename")?: "Untitled"
						byteArrays.add(("\"" + encode(entry.key) + "\"; filename=\"" + filename + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").toByteArray())
						byteArrays.add(value["data"] as ByteArray)
						byteArrays.add("\r\n".toByteArray())
					}
					else -> {
						throw IllegalArgumentException(value.toString())
					}
				}
			} else if (value is File || value is Path) {
				val path = if (value is File) Path.of(value.toURI()) else value as Path
				val mimeType = Files.probeContentType(path)
				byteArrays.add(("\"" + encode(entry.key) + "\"; filename=\"" + path.fileName + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").toByteArray())
				byteArrays.add(Files.readAllBytes(path))
				byteArrays.add("\r\n".toByteArray())
			} else {
				byteArrays.add(("\"" + encode(entry.key) + "\"\r\n\r\n" + entry.value + "\r\n").toByteArray())
			}
		}
		byteArrays.add(("--$boundary--").toByteArray())
		return HttpRequest.BodyPublishers.ofByteArrays(byteArrays)
	}
}