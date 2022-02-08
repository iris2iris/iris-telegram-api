package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Venue {
	/** Venue location. Can't be a live location*/
	val location: Location

	/** Name of the venue */
	val title: String

	/** Address of the venue */
	val address: String

	/** Optional. Foursquare identifier of the venue */
	val foursquare_id: String?

	/** Optional. Foursquare type of the venue. (For example, “arts_entertainment/default”, “arts_entertainment/aquarium” or “food/icecream”.) */
	val foursquare_type: String?

	/** Optional. Google Places identifier of the venue */
	val google_place_id: String?

	/** Optional. Google Places type of the venue. (See supported types.) */
	val google_place_type: String?
}