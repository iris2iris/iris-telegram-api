package iris.tg.api.items

interface ChatLocation {
	/** The location to which the supergroup is connected. Can't be a live location. */
	val location: Location

	/** Location address; 1-64 characters, as defined by the chat owner */
	val address: String
}
