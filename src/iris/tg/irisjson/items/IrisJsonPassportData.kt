package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.EncryptedCredentials
import iris.tg.api.items.EncryptedPassportElement
import iris.tg.api.items.PassportData

open class IrisJsonPassportData(it: JsonItem): IrisJsonTgItem(it), PassportData {
	override val data: List<EncryptedPassportElement>
		get() = TODO("Not yet implemented")
	override val credentials: EncryptedCredentials
		get() = TODO("Not yet implemented")
}
