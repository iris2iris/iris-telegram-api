package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.Location
import iris.tg.pojo.items.Location_Pojo

open class IrisJsonLocation(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Location {
	override val longitude: Float
		get() = source["longitude"].asFloat()
	override val latitude: Float
		get() = source["latitude"].asFloat()
	override val horizontal_accuracy: Float
		get() = source["horizontal_accuracy"].asFloatOrNull() ?: 0f
	override val live_period: Int
		get() = source["live_period"].asIntOrNull() ?: 0
	override val heading: Int
		get() = source["heading"].asIntOrNull() ?: 0
	override val proximity_alert_radius: Int
		get() = source["proximity_alert_radius"].asIntOrNull() ?: 0

	override fun pojo(): Location_Pojo {
		return Location_Pojo(longitude, latitude, horizontal_accuracy, live_period, heading, proximity_alert_radius)
	}
}