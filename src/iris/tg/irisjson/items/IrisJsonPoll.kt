package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.MessageEntity
import iris.tg.api.items.Poll
import iris.tg.api.items.PollOption

open class IrisJsonPoll(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Poll {
	override val id: String by lazyItem { source["id"].asString() }

	override val question: String by lazyItem { source["question"].asString() }

	override val options: List<PollOption>
		get() = source["options"].iterable().map { IrisJsonPollOption(it) }

	override val total_voter_count: Int
		get() = source["total_voter_count"].asInt()
	override val is_closed: Boolean
		get() = source["is_closed"].asBooleanOrNull() ?: false
	override val is_anonymous: Boolean
		get() = source["is_anonymous"].asBooleanOrNull() ?: false
	override val type: String
		get() = source["type"].asString()
	override val allows_multiple_answers: Boolean
		get() = source["allows_multiple_answers"].asBooleanOrNull() ?: false
	override val correct_option_id: Int
		get() = source["correct_option_id"].asIntOrNull() ?: 0
	override val explanation: String?
		get() = source["explanation"].asStringOrNull()

	override val explanation_entities: List<MessageEntity>?
		get() = itemOrNull(source["explanation_entities"]) { it.iterable().map { IrisMessageEntry(it) } }

	override val open_period: Int
		get() = source["open_period"].asInt()
	override val close_date: Int
		get() = source["close_date"].asInt()
}
