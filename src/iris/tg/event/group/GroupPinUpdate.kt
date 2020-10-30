package iris.tg.event.group

import iris.json.JsonItem
import iris.tg.event.PinUpdate

/**
 * @created 28.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
class GroupPinUpdate(source: JsonItem, userIdSource: JsonItem) : GroupChatEvent(source, userIdSource), PinUpdate {

}