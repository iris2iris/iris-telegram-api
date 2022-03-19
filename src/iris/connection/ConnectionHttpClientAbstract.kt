package iris.connection

import iris.connection.query.Query
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

/**
 * @created 25.10.2019
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
abstract class ConnectionHttpClientAbstract<Response, BinaryResponse>(protected val client: HttpClient, private val timeout: Long = 0) : Connection<Response, BinaryResponse> {

	override fun request(url: String, data: String?): Response {
		return request(url, if (data == null) null else HttpRequest.BodyPublishers.ofString(data), HttpResponse.BodyHandlers.ofString())
	}

	override fun request(url: String, data: Query?): Response {
		return request(url, data?.toString())
	}

	fun request(url: String, data: HttpRequest.BodyPublisher?, responseHandler: HttpResponse.BodyHandler<*>, headers: Map<String, String>? = null): Response {
		val builder = HttpRequest.newBuilder()
				.uri(URI.create(url))
		if (timeout > 0)
			builder.timeout(Duration.ofMillis(timeout))
		if (data != null) {
			builder.header("Content-Type", "application/x-www-form-urlencoded")
			builder.POST(data)
		}
		headers?.forEach { (t, u) -> builder.header(t, u) }

		val request = builder.build()

		return customRequest(request, responseHandler)
	}

	protected abstract fun <T>customRequest(request: HttpRequest, responseHandler: HttpResponse.BodyHandler<*>): T

	override fun requestUpload(url: String, files: Map<String, Connection.BinaryData>, data: Query?): Response {
		/*val map = data?.toMutableMap() ?: mutableMapOf()
		map.putAll(files)*/


		val boundary = "testing"
		val request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.POST(ofMimeMultipartData(data, files, boundary))
				.header("Content-Type", "multipart/form-data; boundary=\"$boundary\"")
				.also { if (timeout > 0) it.timeout(Duration.ofMillis(timeout)) }
				.build()

		return customRequest(request, HttpResponse.BodyHandlers.ofString())
	}

	override fun requestByteArray(url: String): BinaryResponse {
		val request = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.also { if (timeout > 0) it.timeout(Duration.ofMillis(timeout)) }
			.build()
		return customRequest(request, HttpResponse.BodyHandlers.ofByteArray())
	}

	private fun ofMimeMultipartData(data: Query?, files: Map<String, Connection.BinaryData>, boundary: String): HttpRequest.BodyPublisher {
		val byteArrays = ArrayList<ByteArray>()
		val separator = ("--$boundary\r\nContent-Disposition: form-data; name=").toByteArray()
		data?.apply {
			for ((key, value) in this.toList()) {
				byteArrays.add(separator)
				byteArrays.add(("\"" + Query.encode(key) + "\"\r\n\r\n" + (value?.toString() ?: "") + "\r\n").toByteArray())
			}
		}

		for ((key, value) in files) {
			byteArrays.add(separator)
			val mimeType = value.mimeType
			val filename = value.fileName
			byteArrays.add(("\"" + Query.encode(key) + "\"; filename=\"" + filename + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").toByteArray())
			byteArrays.add(value.binary())
			byteArrays.add("\r\n".toByteArray())
		}

		/*for (entry in dataItem.entries) {
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
				byteArrays.add(("\"" + encode(entry.key) + "\"\r\n\r\n" + encode(entry.value) + "\r\n").toByteArray())
			}
		}*/
		byteArrays.add(("--$boundary--").toByteArray())
		return HttpRequest.BodyPublishers.ofByteArrays(byteArrays)
	}
}