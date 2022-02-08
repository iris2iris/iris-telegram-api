package iris.tg.api.response

import iris.tg.api.items.ChatMember

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface GetChatMemberResponse : TgResponse {
	override val result: ChatMember?
}