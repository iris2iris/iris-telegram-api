package iris.tg.pojo.items

import iris.tg.api.items.ChatLocation
import iris.tg.api.items.Location

/**
 * @created 10.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class ChatLocation_Pojo(override val location: Location
	, override val address: String
): ChatLocation