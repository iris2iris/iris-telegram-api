package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.VoiceChatStarted

open class IrisJsonVoiceChatStarted(it: JsonItem): IrisJsonTgItem(it), VoiceChatStarted {

}
