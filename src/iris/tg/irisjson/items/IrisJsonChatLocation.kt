package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.ChatLocation
import iris.tg.pojo.items.ChatLocation_Pojo

class IrisJsonChatLocation(it: JsonItem) : IrisJsonTgItem(it), ChatLocation {
	override val location
		get() = IrisJsonLocation(source["location"])
	override val address
		get() = source["address"].asString()

	override fun pojo() : ChatLocation {
		return ChatLocation_Pojo(location.pojo(), address)
	}
}
