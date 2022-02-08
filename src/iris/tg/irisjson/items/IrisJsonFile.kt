package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.File

open class IrisJsonFile(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), File {
	override val fileId: String
		get() = source["file_id"].asString()
	override val fileUniqueId: String
		get() = source["file_unique_id"].asString()
	override val fileSize: Int
		get() = source["file_size"].asInt()
	override val filePath: String?
		get() = source["file_path"].asStringOrNull()
}