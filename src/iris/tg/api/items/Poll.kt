package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Poll {
	/** Unique poll identifier */
	val id: String

	/** Poll question, 1-300 characters */
	val question: String

	/** List of poll options */
	val options: List<PollOption>

	/** Total number of users that voted in the poll */
	val total_voter_count: Int

	/** True, if the poll is closed */
	val is_closed: Boolean

	/** True, if the poll is anonymous */
	val is_anonymous: Boolean

	/** Poll type, currently can be “regular” or “quiz” */
	val type: String

	/** True, if the poll allows multiple answers */
	val allows_multiple_answers: Boolean

	/** Optional. 0-based identifier of the correct answer option. Available only for polls in the quiz mode, which are closed, or was sent (not forwarded) by the botData or to the private chat with the botData. */
	val correct_option_id: Int

	/** Optional. Text that is shown when a user chooses an incorrect answer or taps on the lamp icon in a quiz-style poll, 0-200 characters */
	val explanation: String?

	/** Optional. Special entities like usernames, URLs, botData commands, etc. that appear in the explanation */
	val explanation_entities: List<MessageEntity>?

	/** Optional. Amount of time in seconds the poll will be active after creation */
	val open_period: Int

	/** Optional. Point in time (Unix timestamp) when the poll will be automatically closed */
	val close_date: Int
}