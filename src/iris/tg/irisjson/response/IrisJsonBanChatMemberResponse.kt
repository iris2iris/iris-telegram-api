package iris.tg.irisjson.response

import iris.json.JsonItem
import iris.tg.api.response.BanChatMemberResponse

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonBanChatMemberResponse(source: JsonItem) : IrisJsonBooleanResponse(source), BanChatMemberResponse {
}