package iris.tg.event

/**
 * @created 28.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
interface ChatEvent: Event {
	val id: Long
	val peerId: Long
	val fromId: Long
	val chatId: Long
	val date: Long
	val userId: Long
	val conversationMessageId: Int
}