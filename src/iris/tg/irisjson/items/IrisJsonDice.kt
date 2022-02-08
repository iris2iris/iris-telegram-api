package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.Dice

open class IrisJsonDice(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Dice {
	override val emoji: String
		get() = source["emoji"].asString()
	override val value: Int
		get() = source["value"].asInt()
}
