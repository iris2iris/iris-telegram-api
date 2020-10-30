package iris.tg.api

import iris.util.Options


/**
 * @created 28.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
interface Requester<SingleType> {
	fun request(method: String, options: Options?, token: String? = null): SingleType
	fun requestUpload(method: String, options: Options?, data: Map<String, Options>, token: String? = null): SingleType
}