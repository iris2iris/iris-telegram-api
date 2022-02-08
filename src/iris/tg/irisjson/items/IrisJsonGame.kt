package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.Animation
import iris.tg.api.items.Game
import iris.tg.api.items.MessageEntity
import iris.tg.api.items.PhotoSize

open class IrisJsonGame(sourceItem: JsonItem): IrisJsonTgItem(sourceItem), Game {
	override val title: String
		get() = source["title"].asString()
	override val description: String
		get() = source["description"].asString()
	override val photo: List<PhotoSize>
		get() = source["photo"].iterable().map { IrisJsonPhotoSize(it) }
	override val text: String?
		get() = source["text"].asStringOrNull()
	override val text_entities: List<MessageEntity>?
		get() = itemOrNull(source["text_entities"]) { it.iterable().map { IrisMessageEntry(it) } }
	override val animation: Animation?
		get() = itemOrNull(source["animation"]) { IrisJsonAnimation(it) }
}
