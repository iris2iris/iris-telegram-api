package iris.tg.py.response

import iris.json.JsonItem
import iris.tg.api.TgApiObjFuture
import iris.tg.irisjson.response.IrisJsonGetUpdatesResponse
import iris.tg.py.items.PyUpdate

class PyGetUpdateResponse(api: TgApiObjFuture, source: JsonItem): IrisJsonGetUpdatesResponse(source), PyResponse {
	override val result: List<PyUpdate>? by lazy { with(source["result"]) { iterable().map { PyUpdate(api, it) } }}
}

