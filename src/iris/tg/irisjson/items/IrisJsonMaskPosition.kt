package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.MaskPosition

open class IrisJsonMaskPosition(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), MaskPosition {
	override val point: String
		get() = source["point"].asString()
	override val x_shift: Float
		get() = source["x_shift"].asFloat()
	override val y_shift: Float
		get() = source["y_shift"].asFloat()
	override val scale: Float
		get() = source["scale"].asFloat()
}