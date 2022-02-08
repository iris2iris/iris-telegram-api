package iris.tg.pojo.items

import iris.tg.api.items.MessageEntity
import iris.tg.api.items.User

class MessageEntity_Pojo(
	override val type: String
	, override val offset: Int
	, override val length: Int
	, override val url: String? = null
	, override val user: User? = null
	, override val language: String? = null
) : MessageEntity