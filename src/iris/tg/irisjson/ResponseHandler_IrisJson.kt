package iris.tg.irisjson

import iris.json.JsonItem
import iris.json.flow.JsonFlowParser
import iris.tg.api.ResponseHandler
import java.io.InputStream

/**
 * @created 25.01.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class ResponseHandler_IrisJson : ResponseHandler<JsonItem?> {
	override fun process(method: String, data: String?): JsonItem? {
		if (data == null) return null
		return JsonFlowParser.start(data)
	}

	override fun process(method: String, inputStream: InputStream): JsonItem? {
		return process(method, inputStream.reader(Charsets.UTF_8).readText())
	}
}