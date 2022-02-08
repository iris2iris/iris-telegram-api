package iris.tg.api

import java.io.InputStream

interface ResponseHandler<out ResponseType> {
	fun process(method: String, data: String?): ResponseType
	fun process(method: String, inputStream: InputStream): ResponseType
}