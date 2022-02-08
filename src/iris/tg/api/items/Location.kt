package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Location {

	/** Longitude as defined by sender */
	val longitude: Float

	/** Latitude as defined by sender */
	val latitude: Float

	/** Optional. The radius of uncertainty for the location, measured in meters; 0-1500 */
	val horizontal_accuracy: Float

	/** Optional. Time relative to the message sending date, during which the location can be updated; in seconds. For active live locations only. */
	val live_period: Int

	/** Optional. The direction in which user is moving, in degrees; 1-360. For active live locations only. */
	val heading: Int

	/** Optional. Maximum distance for proximity alerts about approaching another chat member, in meters. For sent live locations only. */
	val proximity_alert_radius: Int
}