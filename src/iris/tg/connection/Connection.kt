package iris.tg.connection

import iris.tg.api.Options
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.nio.file.Files

/**
 * @created 07.09.2019
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Connection<Response, BinaryResponse> {
	fun request(url:String, data: Options?): Response
	fun request(url:String, data:String? = null): Response
	fun requestUpload(url:String, files:Map<String, BinaryData>, data: Options? = null): Response
	fun requestByteArray(url: String): BinaryResponse

	abstract class BinaryData(val mimeType: String? = null, val fileName: String? = null) {
		abstract fun binary(): ByteArray
		abstract fun stream(): InputStream
	}

	class BinaryDataFile(val file: File, mimeType: String? = Files.probeContentType(file.toPath()), fileName: String? = file.name) : BinaryData(mimeType, fileName) {
		override fun binary(): ByteArray = file.readBytes()
		override fun stream(): InputStream = file.inputStream()
	}

	class BinaryDataArray(val bytes: ByteArray, mimeType: String? = null, fileName: String? = null): BinaryData(mimeType, fileName) {
		override fun binary(): ByteArray = bytes
		override fun stream(): InputStream = ByteArrayInputStream(bytes)
	}
}