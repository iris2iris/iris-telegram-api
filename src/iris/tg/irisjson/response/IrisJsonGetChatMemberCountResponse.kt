package iris.tg.irisjson.response

import iris.json.JsonItem
import iris.tg.api.response.GetChatMemberCountResponse

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonGetChatMemberCountResponse(source: JsonItem) : IrisJsonIntegerResponse(source), GetChatMemberCountResponse {
}