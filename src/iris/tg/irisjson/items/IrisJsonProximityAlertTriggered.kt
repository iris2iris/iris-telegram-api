package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.ProximityAlertTriggered
import iris.tg.api.items.User

open class IrisJsonProximityAlertTriggered(it: JsonItem): IrisJsonTgItem(it), ProximityAlertTriggered {

	override val traveler: User by lazyItem { IrisJsonUser(source["traveler"]) }

	override val watcher: User by lazyItem { IrisJsonUser(source["watcher"]) }

	override val distance: Int
		get() = source["distance"].asInt()
}
