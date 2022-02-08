package iris.tg.connection

import iris.tg.api.Options
import java.io.File
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

/**
 * @created 25.10.2019
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
abstract class ConnectionHttpClientAbstract<Response, BinaryResponse>(protected val client: HttpClient) : Connection<Response, BinaryResponse> {

	override fun request(url: String, data: String?): Response {
		return request(url, if (data == null) null else HttpRequest.BodyPublishers.ofString(data), HttpResponse.BodyHandlers.ofString())
	}

	override fun request(url: String, data: Options?): Response {
		return request(url, encodeOptions(data))
	}

	fun request(url: String, data: HttpRequest.BodyPublisher?, responseHandler: HttpResponse.BodyHandler<*>, headers: Map<String, String>? = null): Response {
		var builder = HttpRequest.newBuilder()
				.uri(URI.create(url))
		if (data != null) {
			builder.header("Content-Type", "application/x-www-form-urlencoded")
			builder = builder.POST(data)
		}
		headers?.forEach { (t, u) -> builder.header(t, u) }

		val request = builder.build()

		return customRequest(request, responseHandler)
	}

	protected abstract fun <T>customRequest(request: HttpRequest, responseHandler: HttpResponse.BodyHandler<*>): T

	override fun requestUpload(url: String, files: Map<String, Connection.BinaryData>, data: Options?): Response {
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

	private fun encodeOptions(obj: Options?): String? {
		if (obj.isNullOrEmpty()) return null
		val sb = StringBuilder()
		for ((key, value) in obj.entries) {
			sb.append(encode(key)).append('=')
			.append(encode(value.toString())).append("&")
		}
		return sb.toString()
	}

	private fun ofMimeMultipartData(dataItem: Options, boundary: String): HttpRequest.BodyPublisher {
		val byteArrays = ArrayList<ByteArray>()
		val separator = ("--$boundary\r\nContent-Disposition: form-data; name=").toByteArray()

		for (entry in dataItem.entries) {
			val value = entry.value ?: continue
			byteArrays.add(separator)
			if (value is Connection.BinaryData) {
				val mimeType = value.mimeType
				val filename = value.fileName
				byteArrays.add(("\"" + encode(entry.key) + "\"; filename=\"" + filename + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").toByteArray())
				byteArrays.add(value.binary())
				byteArrays.add("\r\n".toByteArray())
			} else if (value is Map<*, *>) {
				when {
					value["file"] != null -> {
						val path = if (value["file"] is File) Path.of((value["file"] as File).toURI()) else Path.of(value["file"] as String)
						val mimeType = if (value["Content-Type"] != null) value["Content-Type"] else Files.probeContentType(path)
						val filename = value["filename"] ?: path.fileName
						byteArrays.add(("\"" + encode(entry.key) + "\"; filename=\"" + filename + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").toByteArray())
						byteArrays.add(Files.readAllBytes(path))
						byteArrays.add("\r\n".toByteArray())
					}
					value["data"] is ByteArray -> {
						val mimeType = if (value["Content-Type"] != null) value["Content-Type"] else "application/octet-stream"
						val filename = value["filename"]?: "Untitled"
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