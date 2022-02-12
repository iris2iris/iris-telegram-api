package iris.tg.pojo.items

import iris.tg.api.items.Location

/**
 * @created 10.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class Location_Pojo(
	override val longitude: Float,
	override val latitude: Float,
	override val horizontal_accuracy: Float,
	override val live_period: Int,
	override val heading: Int,
	override val proximity_alert_radius: Int
): Location