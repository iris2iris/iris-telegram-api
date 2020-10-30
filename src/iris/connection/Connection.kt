package iris.connection

import iris.util.Options

/**
 * @created 07.09.2019
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
interface Connection<Response, BinaryResponse> {
	fun request(url:String, data: Map<String, Any>?): Response
	fun request(url:String, data:String? = null): Response
	fun requestUpload(url:String, files:Map<String, Options>, data: Map<String, Any>? = null): Response
	fun requestByteArray(url: String): BinaryResponse
}