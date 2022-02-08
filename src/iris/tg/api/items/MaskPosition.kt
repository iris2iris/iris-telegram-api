package iris.tg.api.items

interface MaskPosition: TgItem {
	val point: String
	val x_shift: Float
	val y_shift: Float
	val scale: Float
}
