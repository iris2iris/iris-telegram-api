package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.User
import iris.tg.api.items.VoiceChatParticipantsInvited

open class IrisJsonVoiceChatParticipantsInvited(it: JsonItem): IrisJsonTgItem(it), VoiceChatParticipantsInvited {
	override val users: List<User> by lazyItem { source["users"].iterable().map { IrisJsonUser(it) } }
}
