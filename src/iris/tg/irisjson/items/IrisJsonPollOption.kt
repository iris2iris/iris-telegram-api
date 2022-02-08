package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.PollOption

/**
 * @created 02.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonPollOption(sourceItem: JsonItem): IrisJsonTgItem(sourceItem), PollOption {
	override val text: String
		get() = source["text"].asString()
	override val voter_count: Int
		get() = source["voter_count"].asInt()
}