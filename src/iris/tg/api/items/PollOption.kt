package iris.tg.api.items

interface PollOption {
	/** Option text, 1-100 characters */
	val text: String
	/** Number of users that voted for this option */
	val voter_count: Int
}
