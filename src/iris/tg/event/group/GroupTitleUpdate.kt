package iris.tg.event.group

import iris.json.JsonItem
import iris.tg.event.TitleUpdate

/**
 * @created 28.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
class GroupTitleUpdate(source: JsonItem, userIdSource: JsonItem) : GroupChatEvent(source, userIdSource), TitleUpdate {

}