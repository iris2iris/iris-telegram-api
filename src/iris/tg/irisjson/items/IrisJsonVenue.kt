package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.Location
import iris.tg.api.items.Venue

open class IrisJsonVenue(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Venue {
	override val location: Location
		get() = IrisJsonLocation(source["location"])
	override val title: String
		get() = TODO("Not yet implemented")
	override val address: String
		get() = TODO("Not yet implemented")
	override val foursquare_id: String?
		get() = TODO("Not yet implemented")
	override val foursquare_type: String?
		get() = TODO("Not yet implemented")
	override val google_place_id: String?
		get() = TODO("Not yet implemented")
	override val google_place_type: String?
		get() = TODO("Not yet implemented")
}
